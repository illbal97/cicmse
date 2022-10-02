package com.modern_inf.management.repository;

import com.modern_inf.management.model.JwtRefreshToken;
import com.modern_inf.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtRefreshTokenDao extends JpaRepository<JwtRefreshToken, String> {
    Optional<List<JwtRefreshToken>> findByUserId(Long userId);
}
