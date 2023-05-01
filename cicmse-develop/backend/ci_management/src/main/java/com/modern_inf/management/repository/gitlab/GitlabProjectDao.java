package com.modern_inf.management.repository.gitlab;

import com.modern_inf.management.model.gitlab.GitlabProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GitlabProjectDao extends JpaRepository<GitlabProject, Long> {
    Optional<GitlabProject> findById(Long projectId);

}
