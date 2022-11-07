package com.modern_inf.management.service.gitlab;

import com.modern_inf.management.model.Dto.gitlab.GitlabDto;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.gitlab.GitlabAccount;
import com.modern_inf.management.model.gitlab.GitlabProject;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GitlabService {

    public ResponseEntity<GitlabProject[]> getGitlabProjects(GitlabDto dto) throws GitLabApiException;

    List<GitlabProject> getAllGitlabProjects();

    void updateGitlabTokenExpirationTime(GitlabAccount g);

    void saveGitlabProject(GitlabProject gitlabProject);

    void setGitLabAccountForUser(User user);
}
