package com.modern_inf.management.repository;

import com.modern_inf.management.model.AsanaWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsanaWorkspacesDao extends JpaRepository<AsanaWorkspace, Long> {

    AsanaWorkspace findByGid(String gid);
}
