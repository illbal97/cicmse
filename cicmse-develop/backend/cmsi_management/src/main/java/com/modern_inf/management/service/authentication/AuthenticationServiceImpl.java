package com.modern_inf.management.service.authentication;

import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.JwtRefreshToken;
import com.modern_inf.management.model.User;
import com.modern_inf.management.security.UserPrincipal;
import com.modern_inf.management.security.jwt.JwtProviderImpl;
import com.modern_inf.management.service.jasonWebToken.JwtRefreshTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtProviderImpl jwtProvider;
    private final JwtRefreshTokenServiceImpl jwtRefreshTokenService;
    private final SymmetricEncryption symmetricEncryption;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtProviderImpl jwtProvider,
                                     JwtRefreshTokenServiceImpl jwtRefreshTokenService, SymmetricEncryption symmetricEncryption) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
        this.symmetricEncryption = symmetricEncryption;
    }

    @Override
    public User signIn(User signRequestUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signRequestUser.getUsername(), signRequestUser.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User signUser = userPrincipal.getUser();

        List<JwtRefreshToken> jwtRefreshTokens = jwtRefreshTokenService.getRefreshTokens(signUser.getId());
        List<String> invalidRefreshTokenIds = jwtRefreshTokenService.getInvalidRefreshToken(jwtRefreshTokens);
        List<String> validRefreshTokenIds = jwtRefreshTokenService.getValidRefreshToken(jwtRefreshTokens);

        String refreshTokenId = "";
        if (jwtRefreshTokens == null || jwtRefreshTokens.isEmpty()) { // User doesn't have refresh token
            refreshTokenId = jwtRefreshTokenService.createRefreshToken(signUser.getId()).getTokenId();
        } else if (!invalidRefreshTokenIds.isEmpty()) { // User already has refresh token, and delete invalid refresh tokens if we have
            jwtRefreshTokenService.deleteInvalidRefreshTokens(invalidRefreshTokenIds);
            refreshTokenId = jwtRefreshTokenService.createRefreshToken(signUser.getId()).getTokenId();
        } else if (!validRefreshTokenIds.isEmpty()) { // User has valid refresh token
            refreshTokenId = validRefreshTokenIds.get(0);
        }

        String token = jwtProvider.generateToken(userPrincipal);
        if (!Objects.equals(signUser.getAsanaPersonalAccessToken(), "") && signUser.getAsanaPersonalAccessToken() != null) {
            try {
                signUser.setAsanaPersonalAccessToken(this.symmetricEncryption.decrypt(signUser.getAsanaPersonalAccessToken()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        signUser.setAccessToken(token);
        signUser.setRefreshToken(refreshTokenId);

        return signUser;
    }
}
