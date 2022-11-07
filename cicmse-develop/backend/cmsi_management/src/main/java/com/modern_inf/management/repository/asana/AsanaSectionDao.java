package com.modern_inf.management.repository.asana;

import com.modern_inf.management.model.asana.AsanaSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsanaSectionDao extends JpaRepository<AsanaSection, Long> {

    AsanaSection findByGid(String gid);
}
