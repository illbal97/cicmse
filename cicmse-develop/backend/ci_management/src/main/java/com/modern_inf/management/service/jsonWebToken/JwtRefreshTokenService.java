package com.modern_inf.management.service.jsonWebToken;

import com.modern_inf.management.model.JwtRefreshToken;

public interface JwtRefreshTokenService {

    JwtRefreshToken createRefreshToken(Long userId);

    JwtRefreshToken getRefreshTokenByUser(Long id);

    void deleteRefreshToken(JwtRefreshToken refreshTokenId);

    String generateAccessTokenFromRefreshToken(Long userId);


}
