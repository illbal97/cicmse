package com.modern_inf.management.repository;

import com.modern_inf.management.model.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRefreshTokenDao extends JpaRepository<JwtRefreshToken, String> {
    Optional<JwtRefreshToken> findByUserId(Long userId);

}
