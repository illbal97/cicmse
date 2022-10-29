package com.modern_inf.management.repository;

import com.modern_inf.management.model.AsanaSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsanaSectionDao extends JpaRepository<AsanaSection, Long> {

    AsanaSection findByGid(String gid);
}
