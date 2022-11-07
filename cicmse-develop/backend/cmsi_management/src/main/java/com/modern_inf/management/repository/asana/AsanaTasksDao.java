package com.modern_inf.management.repository.asana;

import com.modern_inf.management.model.asana.AsanaTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsanaTasksDao extends JpaRepository<AsanaTask, Long> {

    AsanaTask findByGid(String gid);

}
