package com.modern_inf.management.repository;

import com.modern_inf.management.model.Asana;
import com.modern_inf.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsanaDao extends JpaRepository<Asana, Long> {

    Asana findByUserId(Long id);
}
