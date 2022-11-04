package com.modern_inf.management.service.jasonWebToken;

import com.modern_inf.management.model.JwtRefreshToken;
import com.modern_inf.management.model.User;

import java.util.List;

public interface JwtRefreshTokenService {

    JwtRefreshToken createRefreshToken(Long userId);

    User generateAccessTokenFromRefreshToken(String refreshTokenId);

    List<JwtRefreshToken> getRefreshTokens(Long id);

    List<String> getInvalidRefreshToken(List<JwtRefreshToken> refreshTokens);

    List<String> getValidRefreshToken(List<JwtRefreshToken> refreshTokens);

    void deleteInvalidRefreshTokens(List<String> invalidRefreshTokens);
}
