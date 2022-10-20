package com.modern_inf.management.controller;

import com.asana.models.Project;
import com.asana.models.Task;
import com.asana.models.Workspace;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.*;
import com.modern_inf.management.model.Dto.AsanaProjectDto;
import com.modern_inf.management.model.Dto.AsanaProjectTasksDto;
import com.modern_inf.management.model.Dto.UserAndWorkspaceGidDto;
import com.modern_inf.management.service.AsanaService;
import com.modern_inf.management.service.AsanaServiceImpl;
import com.modern_inf.management.service.UserService;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("api/v1/asana")
public class AsanaController {

    private final UserService userService;
    private final AsanaServiceImpl asanaService;
    private final SymmetricEncryption symmetricEncryption;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final  DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final  List<String> error = new ArrayList<>();



    @Autowired
    public AsanaController(UserService userService, AsanaServiceImpl asanaWorkspaces, AsanaService asanaService, SymmetricEncryption symmetricEncryption) {
        this.userService = userService;
        this.asanaService = asanaWorkspaces;
        this.symmetricEncryption = symmetricEncryption;

    }

    @PostMapping("asanaUser")
    public ResponseEntity<?> getAsanaUser(@RequestBody UserAndWorkspaceGidDto dto) {
        List<com.asana.models.User> asanaUser = new ArrayList<>();

        try {
            asanaUser = this.asanaService.getAsanaUsers(dto);
        }catch (ConnectTimeoutException e) {
            LOGGER.error(e.getMessage());
            error.clear();
            error.add("Connection timeout");
            return  ResponseEntity.ok(error);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return ResponseEntity.ok(asanaUser);
    }

    @PostMapping("workspaces")
    public ResponseEntity<?> getAsanaWorkspaces(@RequestBody User user) {
        error.clear();
        if (user.getAsanaPersonalAccessToken() == null || user.getAsanaPersonalAccessToken().isEmpty()) {
            LOGGER.error("Not exist any Asana account for this user: " + user.getName());
            error.add("AsanaPersonalAccessToken is null");
            return ResponseEntity.ok(error);
        }
        var asana = this.asanaService.getAsanaAccountIdByUser(user.getId());

        // If there are not any Asana account we do not have any Asana workspaces
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
                    var asanaWorkspace = buildAsanaWorkspace(workspace, asana);
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

    @PostMapping("projects")
    public ResponseEntity<?> getAsanaProjects(@RequestBody UserAndWorkspaceGidDto dto) {
        error.clear();
        var asana = this.asanaService.getAsanaAccountIdByUser(dto.getUser().getId());
        if (asana != null) {
            var asanaWorkspaces =  asana.getAsanaWorkspaces();
            var asanaProjects = asanaWorkspaces.stream().filter( a -> a.getGid().equals(dto.getWorkspaceGid())).findFirst().get().getAsanaProjects();

            if (!asanaProjects.isEmpty() && LocalDateTime.now().isBefore(asana.getTokenExpirationTime()) && !dto.isImmediate()) {

                // Get data from database cache
                return ResponseEntity.ok(asanaProjects);
            } else {
                try {
                    LocalDate ld = null;
                    LocalDateTime ldt = null;
                    // API
                    List<Project> projects = this.asanaService.getAsanaProjectsByWorkspaces(Optional.of(dto.getUser()), dto.getWorkspaceGid());
                    for (Project project : projects) {
                        if(project.dueOn != null) {
                            ld = LocalDate.parse(project.dueDate.toString(), DATE_FORMATTER);
                            ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
                        }
                        var asanaProject = buildAsanaProject(project, ldt, dto.getWorkspaceGid());

                        if (asanaProjects.stream().noneMatch(x -> x.getGid().equals(asanaProject.getGid()))) {
                            this.asanaService.saveAsanaProject(asanaProject);
                        }
                    }
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

    @PostMapping("projectTasks")
    public ResponseEntity<?> getAsanaTasks(@RequestBody AsanaProjectTasksDto dto) {
        error.clear();
        var asana = this.asanaService.getAsanaAccountIdByUser(dto.getUser().getId());

        if (asana != null) {
            var asanaWorkspaces =  asana.getAsanaWorkspaces();
            var asanaProjects = asanaWorkspaces.stream().filter( a -> a.getGid().equals(dto.getWorkspaceGid())).findFirst().get().getAsanaProjects();
            var asanaTasks = asanaProjects.stream().filter( a -> a.getGid().equals(dto.getProjectGid())).findFirst().get().getAsanaTasks();

            if (!asanaTasks.isEmpty() && LocalDateTime.now().isBefore(asana.getTokenExpirationTime())) {

                // Get data from database cache
                return ResponseEntity.ok(asanaTasks);
            } else {
                try {
                    LocalDate ld = null;
                    LocalDateTime ldt = null;
                    // API
                    List<Task> tasks = this.asanaService.getAsanaTasksFromProject(dto);
                    for (Task task : tasks ) {
                        if(task.dueOn != null) {
                            ld = LocalDate.parse(task.dueOn.toString(), DATE_FORMATTER);
                            ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
                        }
                        var asanaTasks1 = buildAsanaTask(task, ldt, dto);
                        if (asanaTasks.stream().noneMatch(x -> x.getGid().equals(asanaTasks1.getGid()))) {
                            this.asanaService.saveAsanaTask(asanaTasks1);
                        }
                    }
                    this.asanaService.updateAsanaTokenExpirationTime(asana);

                    return ResponseEntity.ok(tasks);
                } catch (ConnectTimeoutException e) {
                    LOGGER.error(e.getMessage());
                    error.add("Connection timeout");
                    return ResponseEntity.ok(error);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    error.add("");

                    return ResponseEntity.ok(error);
                }
            }
        }
        error.add("");
        return  ResponseEntity.ok(error);
    }

    @PostMapping("add-access-token")
    public ResponseEntity<?> setUserPersonalAccessToken(@RequestBody User u) {
        var user = this.userService.findByUsername(u.getUsername());
        try {
            user.get().setAsanaPersonalAccessToken(this.symmetricEncryption.encrypt(u.getAsanaPersonalAccessToken()));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        var us = this.userService.setPersonalAccessTokenForAsana(user.get());
        this.asanaService.setAsanaAccountForUser(user.get());
        try {
            us.setAsanaPersonalAccessToken(this.symmetricEncryption.decrypt(us.getAsanaPersonalAccessToken()));
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        us.setAccessToken(u.getAccessToken());
        us.setRefreshToken(u.getRefreshToken());
        return  ResponseEntity.ok(us);
    }
    @PostMapping("createProject")
    public ResponseEntity createProject(@RequestBody AsanaProjectDto dto) {
        Project project;
        AsanaProjects asanaProject;
        error.clear();

        try {
            project = this.asanaService.createAsanaProject(dto);
            LocalDate ld = LocalDate.parse(project.dueDate.toString(), DATE_FORMATTER);
            LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());

            asanaProject = buildAsanaProject(project, ldt, dto.getWorkspaceGid());

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

    public AsanaProjects buildAsanaProject(Project project, LocalDateTime ldt, String workspaceGid ) {
        return AsanaProjects.builder()
                .name(project.name)
                .gid(project.gid)
                .asanaWorkspaces(this.asanaService.getAsanaWorkspaceByWorkspaceGid(workspaceGid))
                .owner(project.owner != null ? project.owner.name: null)
                .resourceType(project.resourceType)
                .createdAt(project.createdAt != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(project.createdAt.getValue()),
                        TimeZone.getDefault().toZoneId()): null)
                .dueDate(ldt)
                .isPublic(project.isPublic)
                .color(project.color)
                .notes(project.notes)
                .build();
    }

    public AsanaWorkspaces buildAsanaWorkspace(Workspace workspace, Asana asana) {
       return AsanaWorkspaces.builder()
                .name(workspace.name)
                .emailDomains(String.valueOf(workspace.emailDomains))
                .isOrganization(workspace.isOrganization)
                .resourceType(workspace.resourceType)
                .asana(asana)
                .gid(workspace.gid)
                .build();
    }

    public AsanaTasks buildAsanaTask(Task task, LocalDateTime ldt, AsanaProjectTasksDto dto) {
        return AsanaTasks.builder()
                .name(task.name)
                .asanaProjects(this.asanaService.getAsanaProjectByProjectGid(dto.getProjectGid()))
                .createdAt((LocalDateTime.ofInstant(Instant.ofEpochMilli(task.createdAt.getValue()),
                        TimeZone.getDefault().toZoneId())))
                .dueDate(ldt)
                .resourceType(task.resourceType)
                .gid(task.gid)
                .build();
    }
}
