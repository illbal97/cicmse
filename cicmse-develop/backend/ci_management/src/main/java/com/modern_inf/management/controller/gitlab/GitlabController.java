package com.modern_inf.management.controller.gitlab;

import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.dto.gitlab.GitlabDto;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.gitlab.GitlabBranch;
import com.modern_inf.management.model.gitlab.GitlabProject;
import com.modern_inf.management.service.gitlab.GitlabService;
import com.modern_inf.management.service.userService.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/gitlab")
public class GitlabController {


    private final GitlabService gitlabService;

    private final UserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SymmetricEncryption symmetricEncryption;

    private final List<String> error = new ArrayList<>();


    @Autowired
    public GitlabController(GitlabService gitlabService, UserService userService, SymmetricEncryption symmetricEncryption) {
        this.gitlabService = gitlabService;
        this.userService = userService;
        this.symmetricEncryption = symmetricEncryption;
    }

    @PostMapping("projects")
    public ResponseEntity<?> getGitlabProjects(@RequestBody GitlabDto dto) {
        error.clear();
        var user = userService.findUserById(Long.parseLong(dto.getUserId()));
        if (user.isPresent()) {
            dto.setUser(user.get());
            var gitlabAccount = user.get().getGitlabAccount();
            List<GitlabProject> existGitlabProjects = this.gitlabService.getAllGitlabProjects();
            ResponseEntity<GitlabProject[]> gitlabProjects = null;
            if (gitlabAccount != null) {
                if (!existGitlabProjects.isEmpty() && LocalDateTime.now().isBefore(gitlabAccount.getCacheExpirationTime()) && !dto.isImmediate()) {
                    return ResponseEntity.ok().body(existGitlabProjects);
                }
                try {
                    gitlabProjects = this.gitlabService.getGitlabProjects(dto);
                    for (GitlabProject gitlabProject : Objects.requireNonNull(gitlabProjects.getBody())) {
                        if (existGitlabProjects.stream().noneMatch(g -> Objects.equals(g.getId(), gitlabProject.getId()))) {
                            this.gitlabService.saveGitlabProject(gitlabProject);
                        }
                    }

                    this.gitlabService.updateGitlabTokenExpirationTime(gitlabAccount);
                    return ResponseEntity.ok().body(gitlabProjects.getBody());
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    error.add("Bad gitlab personal access token");
                    return ResponseEntity.ok(error);
                }
            }else {
                error.add("Bad gitlab personal access token");
                return ResponseEntity.ok().body(error);
            }
        } else {
            error.add("User account was not found");
            return ResponseEntity.ok().body(error);
        }

    }

    @PostMapping("project-creation")
    public ResponseEntity<?> projectCreation(@RequestBody GitlabDto dto) {
        error.clear();
        var user = userService.findUserById(Long.parseLong(dto.getUserId()));
        dto.setUser(user.get());
        try {
            var project = this.gitlabService.gitlabProjectCreation(dto).getBody();
            project.setGitlab(user.get().getGitlabAccount());
            this.gitlabService.saveGitlabProject(project);
            return ResponseEntity.ok(project);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.ok(error);

        }

    }

    @PostMapping("branch-creation")
    public ResponseEntity<?> branchCreation(@RequestBody GitlabDto dto) {
        error.clear();
        var user = userService.findUserById(Long.parseLong(dto.getUserId()));
        dto.setUser(user.get());
        try {
            GitlabBranch gitlabBranch;
            gitlabBranch = this.gitlabService.gitlabBranchCreation(dto).getBody();
            var gitlabProject = this.gitlabService.getGitlabProjectById(dto.getGitlabProjectId());
            assert gitlabBranch != null;
            gitlabBranch.setGitlabProject(gitlabProject.get());
            this.gitlabService.saveGitlabBranch(gitlabBranch);
            return ResponseEntity.ok(gitlabBranch);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.ok(error);

        }

    }

    @PostMapping("add-access-token")
    public ResponseEntity<?> setUserPersonalAccessToken(@RequestBody User u) {
        var user = this.userService.findByUsername(u.getUsername());
        try {
            user.get().setGitlabPersonalAccessToken(this.symmetricEncryption.encrypt(u.getGitlabPersonalAccessToken()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        var us = this.userService.setPersonalAccessTokenForGitlab(user.get());
        this.gitlabService.setGitLabAccountForUser(user.get());

        return ResponseEntity.ok(us);
    }

}
