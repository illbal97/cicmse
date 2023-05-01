package com.modern_inf.management.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieFactory {

    @Value("${app.cookie.jwt.token.expiration.time.in.second}")
    private  Long COOKIE_JWT_TOKEN_EXPIRATION_TIME;

    @Value("${app.cookie.rf.token.expiration.time.in.second}")
    private  Long COOKIE_REFRESH_TOKEN_EXPIRATION_TIME;

    public  ResponseCookie createJwtTokenCookie(String token) {
       return  ResponseCookie.from("jwt_token", token) // key & value
                .secure(false)
                .path("/")

                // path
                .maxAge(COOKIE_JWT_TOKEN_EXPIRATION_TIME)
                .sameSite("Strict")
                .httpOnly(true)

                .build();
    }

    public  ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return  ResponseCookie.from("rf_token", refreshToken) // key & value
                .secure(false)
                .path("/")

                // path
                .maxAge(COOKIE_REFRESH_TOKEN_EXPIRATION_TIME)
                .sameSite("Strict")
                .httpOnly(true)

                .build();
    }

    public  ResponseCookie deleteCookie(String tokenType) {
        return  ResponseCookie.from(tokenType, null) // key & value
                .secure(false)
                .path("/")

                // path
                .maxAge(0)
                .sameSite("Strict")
                .httpOnly(true)

                .build();
    }
}
