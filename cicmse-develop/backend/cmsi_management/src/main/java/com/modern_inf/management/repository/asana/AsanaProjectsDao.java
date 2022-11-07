package com.modern_inf.management.repository.asana;

import com.modern_inf.management.model.asana.AsanaProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsanaProjectsDao extends JpaRepository<AsanaProject, Long> {
    AsanaProject findByGid(String gid);

}
