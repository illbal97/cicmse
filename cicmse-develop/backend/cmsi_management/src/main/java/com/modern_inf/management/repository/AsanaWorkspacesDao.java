package com.modern_inf.management.repository;

import com.modern_inf.management.model.AsanaWorkspaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsanaWorkspacesDao extends JpaRepository<AsanaWorkspaces, Long> {
}
