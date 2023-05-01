package com.modern_inf.management.service.gitlab;

import com.modern_inf.management.model.dto.gitlab.GitlabDto;
import com.modern_inf.management.model.User;
import com.modern_inf.management.model.gitlab.GitlabAccount;
import com.modern_inf.management.model.gitlab.GitlabBranch;
import com.modern_inf.management.model.gitlab.GitlabProject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface GitlabService {

    public ResponseEntity<GitlabProject[]> getGitlabProjects(GitlabDto dto) throws Exception;

    List<GitlabProject> getAllGitlabProjects();

    Optional<GitlabProject> getGitlabProjectById(Long projectId);

    ResponseEntity<GitlabProject> gitlabProjectCreation(GitlabDto dto) throws Exception;

    ResponseEntity<GitlabBranch> gitlabBranchCreation(GitlabDto dto) throws Exception;

    void updateGitlabTokenExpirationTime(GitlabAccount g);

    void saveGitlabProject(GitlabProject gitlabProject);

    void setGitLabAccountForUser(User user);

    void saveGitlabBranch(GitlabBranch branch);
}
