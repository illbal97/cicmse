package com.modern_inf.management.service.authentication;

import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.JwtRefreshToken;
import com.modern_inf.management.model.User;
import com.modern_inf.management.security.UserPrincipal;
import com.modern_inf.management.security.jwt.JwtProviderImpl;
import com.modern_inf.management.service.jsonWebToken.JwtRefreshTokenServiceImpl;
import com.modern_inf.management.service.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtProviderImpl jwtProvider;

    private  final UserServiceImpl userService;
    private final JwtRefreshTokenServiceImpl jwtRefreshTokenService;
    private final SymmetricEncryption symmetricEncryption;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtProviderImpl jwtProvider,
                                     UserServiceImpl userService, JwtRefreshTokenServiceImpl jwtRefreshTokenService, SymmetricEncryption symmetricEncryption) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
        this.symmetricEncryption = symmetricEncryption;
    }

    @Override
    public UserPrincipal signIn(User signRequestUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signRequestUser.getUsername(), signRequestUser.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User signUser = userPrincipal.getUser();

        if (signUser == null) {
            return null;
        }
        return userPrincipal;
    }

    @Override
    public void logOut(User requestUser) {
         var user = this.userService.findUserById(requestUser.getId());
         if (user.isPresent()) {
            var rfToken = this.jwtRefreshTokenService.getRefreshTokenByUser(user.get().getId());
            this.jwtRefreshTokenService.deleteRefreshToken(rfToken);
         }

    }

    @Override
    public String setUpRefreshTokenAndReturn(Long userId) {
        JwtRefreshToken jwtRefreshTokens = jwtRefreshTokenService.getRefreshTokenByUser(userId);
        if (jwtRefreshTokens != null) {
            var rfToken = this.jwtRefreshTokenService.getRefreshTokenByUser(userId);
            this.jwtRefreshTokenService.deleteRefreshToken(rfToken);
        }
        var refreshToken = this.jwtRefreshTokenService.createRefreshToken(userId);
        return refreshToken.getTokenId();
    }
}
