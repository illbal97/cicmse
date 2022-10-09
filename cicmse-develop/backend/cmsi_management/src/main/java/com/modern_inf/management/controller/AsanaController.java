package com.modern_inf.management.controller;

import com.asana.models.Project;
import com.asana.models.Workspace;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.AsanaProjects;
import com.modern_inf.management.model.AsanaWorkspaces;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.UserAndWorkspaceGidDto;
import com.modern_inf.management.service.AsanaService;
import com.modern_inf.management.service.AsanaServiceImpl;
import com.modern_inf.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
                } catch (Exception e) {
                    System.out.println(e.getMessage());
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
            var asanaProjects = asanaWorkspaces.stream().filter( a -> a.getGid().equals(dto.getWorkspacesGid())).findFirst().get().getAsanaProjects();

            if (!asanaProjects.isEmpty() && LocalDateTime.now().isBefore(asana.getTokenExpirationTime())) {

                // Get data from database cache
                return ResponseEntity.ok(asanaProjects);
            } else {
                try {

                    // API
                    List<Project> projects = this.asanaService.getAsanaProjectsByWorkspaces(Optional.of(dto.getUser()), dto.getWorkspacesGid());
                    for (Project project : projects) {
                        var asanaProject = AsanaProjects.builder()
                                .name(project.name)
                                .asanaWorkspaces(this.asanaService.getAsanaWorkspaceByWorkspaceGid(dto.getWorkspacesGid()))
                                .currentStatus(String.valueOf(project.currentStatus))
                                .createdAt(project.createdAt)
                                .isPublic(project.isPublic)
                                .dueDate(project.dueDate)
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
                } catch (Exception e) {
                    System.out.println(e.getMessage());
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
}
