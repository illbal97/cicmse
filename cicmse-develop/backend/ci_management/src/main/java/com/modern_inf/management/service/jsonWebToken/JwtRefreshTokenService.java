package com.modern_inf.management.service.jsonWebToken;

import com.modern_inf.management.model.JwtRefreshToken;
import com.modern_inf.management.model.User;

import java.util.List;

public interface JwtRefreshTokenService {

    JwtRefreshToken createRefreshToken(Long userId);

    JwtRefreshToken getRefreshTokenByUser(Long id);

    void deleteRefreshToken(JwtRefreshToken refreshTokenId);

    String generateAccessTokenFromRefreshToken(Long userId);


}
