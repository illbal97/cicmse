package com.modern_inf.management.controller.gitlab;

import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.Dto.gitlab.GitlabDto;
import com.modern_inf.management.model.User;
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
        dto.setUser(user.get());
        var gitlabAccount = user.get().getGitlabAccount();
        List<GitlabProject> existGitlabProjects = this.gitlabService.getAllGitlabProjects();
        ResponseEntity<GitlabProject[]> gitlabProjects = null;

        if(!existGitlabProjects.isEmpty() && LocalDateTime.now().isBefore(gitlabAccount.getTokenExpirationTime())) {
            return ResponseEntity.ok(this.gitlabService.getAllGitlabProjects());
        }
        try {
            gitlabProjects = this.gitlabService.getGitlabProjects(dto);
            for(GitlabProject gitlabProject: Objects.requireNonNull(gitlabProjects.getBody())) {
                if(existGitlabProjects.stream().noneMatch( g -> Objects.equals(g.getId(), gitlabProject.getId()))) {
                    this.gitlabService.saveGitlabProject(gitlabProject);
                }
            }

            this.gitlabService.updateGitlabTokenExpirationTime(gitlabAccount);

            return ResponseEntity.ok(gitlabProjects.getBody());
        } catch (Exception e) {
           LOGGER.error(e.getMessage());
           error.add(e.getMessage());
           return ResponseEntity.ok(error);
        }
    }

    @PostMapping("add-access-token")
    public ResponseEntity<?> setUserPersonalAccessToken(@RequestBody User u) {
        var user = this.userService.findByUsername(u.getUsername());
        try {
            user.get().setAsanaPersonalAccessToken(this.symmetricEncryption.encrypt(u.getAsanaPersonalAccessToken()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        var us = this.userService.setPersonalAccessTokenForAsana(user.get());
        this.gitlabService.setGitLabAccountForUser(user.get());
        try {
            us.setAsanaPersonalAccessToken(this.symmetricEncryption.decrypt(us.getAsanaPersonalAccessToken()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        us.setAccessToken(u.getAccessToken());
        us.setRefreshToken(u.getRefreshToken());
        return ResponseEntity.ok(us);
    }

}
