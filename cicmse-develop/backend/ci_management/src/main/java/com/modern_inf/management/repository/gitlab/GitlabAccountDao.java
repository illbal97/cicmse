package com.modern_inf.management.repository.gitlab;

import com.modern_inf.management.model.gitlab.GitlabAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitlabAccountDao extends JpaRepository<GitlabAccount, Long> {
}
