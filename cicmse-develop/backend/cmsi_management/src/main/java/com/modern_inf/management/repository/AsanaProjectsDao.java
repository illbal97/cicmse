package com.modern_inf.management.repository;

import com.modern_inf.management.model.AsanaProjects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsanaProjectsDao extends JpaRepository<AsanaProjects, Long> {
}
