package com.modern_inf.management.controller;

import com.asana.models.Project;
import com.asana.models.Workspace;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.AsanaProjects;
import com.modern_inf.management.model.AsanaWorkspaces;
import com.modern_inf.management.model.Dto.AsanaProjectDto;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.Dto.UserAndWorkspaceGidDto;
import com.modern_inf.management.service.AsanaService;
import com.modern_inf.management.service.AsanaServiceImpl;
import com.modern_inf.management.service.UserService;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/asana")
public class AsanaController {

    private final UserService userService;
    private final AsanaServiceImpl asanaService;
    private final SymmetricEncryption symmetricEncryption;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public AsanaController(UserService userService, AsanaServiceImpl asanaWorkspaces, AsanaService asanaService, SymmetricEncryption symmetricEncryption) {
        this.userService = userService;
        this.asanaService = asanaWorkspaces;
        this.symmetricEncryption = symmetricEncryption;

    }

    @PostMapping("workspaces")
    public ResponseEntity<?> getAsanaWorkspaces(@RequestBody User user) {
        List<String> error = new ArrayList<>();
        var asana = this.asanaService.getAsanaAccountIdByUser(user.getId());

        // If there are not any Asana account we do not have any Asana workspaces
        if (asana != null) {
            var asanaWorkspaces = this.asanaService.getAllAsanaWorkspaces();
            asanaWorkspaces = asanaWorkspaces.stream().filter(
                    a -> Objects.equals(a.getAsana().getId(), asana.getId()))
                    .toList();

            if (!asanaWorkspaces.isEmpty() && LocalDateTime.now().isBefore(asana.getTokenExpirationTime())) {

                // Get data from database cache
                return ResponseEntity.ok(asanaWorkspaces);
            } else {
                try {

                    // API
                    List<Workspace> workspaces = this.asanaService.getAsanaWorkspaces(Optional.of(user));
                    for (Workspace workspace : workspaces) {
                        var asanaWorkspace = AsanaWorkspaces.builder()
                                .name(workspace.name)
                                .emailDomains(String.valueOf(workspace.emailDomains))
                                .isOrganization(workspace.isOrganization)
                                .resourceType(workspace.resourceType)
                                .asana(asana)
                                .gid(workspace.gid)
                                .build();
                        if (asanaWorkspaces.isEmpty() || asanaWorkspaces.stream().noneMatch(x -> x.getGid().equals(asanaWorkspace.getGid()))) {
                            this.asanaService.saveAsanaWorkspace(asanaWorkspace);
                        }

                    }
                    this.asanaService.updateAsanaTokenExpirationTime(asana);

                    return ResponseEntity.ok(workspaces);
                } catch (ConnectTimeoutException e) {
                    LOGGER.error(e.getMessage());
                    error.add("Connection timeout");
                    return ResponseEntity.ok(error);
                }catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    error.add("Bad asana personal access token");
                    return ResponseEntity.ok(error);
                }
            }
        }
        error.add("Not exist any Asana account for this user: " + user.getName());
        return  ResponseEntity.ok(error);

    }

    @PostMapping("projects")
    public ResponseEntity<?> getAsanaProjects(@RequestBody UserAndWorkspaceGidDto dto) {
        List<String> error = new ArrayList<>();
        var asana = this.asanaService.getAsanaAccountIdByUser(dto.getUser().getId());

        if (asana != null) {
            var asanaWorkspaces =  asana.getAsanaWorkspaces();
            var asanaProjects = asanaWorkspaces.stream().filter( a -> a.getGid().equals(dto.getWorkspaceGid())).findFirst().get().getAsanaProjects();

            if (!asanaProjects.isEmpty() && LocalDateTime.now().isBefore(asana.getTokenExpirationTime()) && !dto.isImmediate()) {

                // Get data from database cache
                return ResponseEntity.ok(asanaProjects);
            } else {
                try {

                    DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // API
                    List<Project> projects = this.asanaService.getAsanaProjectsByWorkspaces(Optional.of(dto.getUser()), dto.getWorkspaceGid());
                    for (Project project : projects) {
                        if(project.dueDate != null) {
                            LocalDate ld = LocalDate.parse(project.dueDate.toString(), DATEFORMATTER);
                            LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
                        }

                        var asanaProject = AsanaProjects.builder()
                                .name(project.name)
                                .asanaWorkspaces(this.asanaService.getAsanaWorkspaceByWorkspaceGid(dto.getWorkspaceGid()))
                                .currentStatus(String.valueOf(project.currentStatus))
                                .createdAt(project.createdAt)
                                .isPublic(project.isPublic)
                                .createdAt(project.createdAt)
                                .dueDate(null)
                                .resourceType(project.resourceType)
                                .color(project.color)
                                .gid(project.gid)
                                .build();
                        if (asanaProjects.stream().noneMatch(x -> x.getGid().equals(asanaProject.getGid()))) {
                            this.asanaService.saveAsanaProject(asanaProject);
                        }

                    }
                    this.asanaService.updateAsanaTokenExpirationTime(asana);

                    return ResponseEntity.ok(projects);
                } catch (ConnectTimeoutException e) {
                    LOGGER.error(e.getMessage());
                    error.add("Connection timeout");
                    return ResponseEntity.ok(error);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    error.add("Bad asana personal access token");

                    return ResponseEntity.ok(error);
                }
            }
        }
        error.add("Not exist any Asana workspace account for this user: " + dto.getUser().getName());
        return  ResponseEntity.ok(error);

    }

    @PostMapping("add-access-token")
    public ResponseEntity<?> setUserPersonalAccessToken(@RequestBody User u) {
        var user = this.userService.findByUsername(u.getUsername());
        try {
            user.get().setAsanaPersonalAccessToken(this.symmetricEncryption.encrypt(u.getAsanaPersonalAccessToken()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        var us = this.userService.setPersonalAccessTokenForAsana(user.get());
        this.asanaService.setAsanaAccountForUser(user.get());
        try {
            us.setAsanaPersonalAccessToken(this.symmetricEncryption.decrypt(us.getAsanaPersonalAccessToken()));
        }catch (Exception e) {
            System.out.println("Problem: "  + e.getMessage());
        }
        us.setAccessToken(u.getAccessToken());
        us.setRefreshToken(u.getRefreshToken());
        return  ResponseEntity.ok(us);
    }
    @PostMapping("createProject")
    public ResponseEntity createProject(@RequestBody AsanaProjectDto asanaProjectDto) {
        List<String> error = new ArrayList<>();
        Project project;
        AsanaProjects asanaProject;

        try {
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            project = this.asanaService.createAsanaProject(asanaProjectDto);
            LocalDate ld = LocalDate.parse(project.dueDate.toString(), DATEFORMATTER);
            LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
            asanaProject = AsanaProjects.builder()
                    .name(project.name)
                    .gid(project.gid)
                    .asanaWorkspaces(this.asanaService.getAsanaWorkspaceByWorkspaceGid(asanaProjectDto.getWorkspaceGid()))
                    .owner(String.valueOf(project.owner))
                    .resourceType(project.resourceType)
                    .createdAt(project.createdAt)
                    .dueDate(ldt)
                    .isPublic(project.isPublic)
                    .color(project.color)
                    .notes(project.notes)
                    .build();
            if (this.asanaService.getAllAsanaProjects().stream().noneMatch(a -> a.getName().equals(asanaProject.getName()))) {
                this.asanaService.saveAsanaProject(asanaProject);
                return ResponseEntity.ok(asanaProject);
            }
        } catch (ConnectTimeoutException e) {
            LOGGER.error(e.getMessage());
            error.add("Connection timeout");
            return ResponseEntity.ok(error);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return  ResponseEntity.status(403).body("This project name is already taken");

    }
}
