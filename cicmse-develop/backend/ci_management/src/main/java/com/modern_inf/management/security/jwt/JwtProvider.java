package com.modern_inf.management.security.jwt;

import com.modern_inf.management.security.UserPrincipal;
import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String generateToken(UserPrincipal userAuth);

    Authentication getAuthentication(String  token);

    boolean isTokenValid(String token);

}
