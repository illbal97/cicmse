package com.modern_inf.management.repository;

import com.modern_inf.management.model.AsanaProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsanaProjectsDao extends JpaRepository<AsanaProject, Long> {
    AsanaProject findByGid(String gid);

}
