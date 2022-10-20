package com.modern_inf.management.repository;

import com.modern_inf.management.model.AsanaTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsanaTasksDao extends JpaRepository<AsanaTasks, Long> {
}
