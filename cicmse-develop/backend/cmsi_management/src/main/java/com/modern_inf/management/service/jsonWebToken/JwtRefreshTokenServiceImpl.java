package com.modern_inf.management.service.jsonWebToken;

import com.modern_inf.management.helper.SecurityUtils;
import com.modern_inf.management.helper.SymmetricEncryption;
import com.modern_inf.management.model.JwtRefreshToken;
import com.modern_inf.management.model.User;
import com.modern_inf.management.repository.JwtRefreshTokenDao;
import com.modern_inf.management.repository.UserDao;
import com.modern_inf.management.security.UserPrincipal;
import com.modern_inf.management.security.jwt.JwtProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService {

    private final JwtRefreshTokenDao jwtRefreshTokenDao;
    private final UserDao userDao;
    private final JwtProviderImpl jwtProvider;
    private final SymmetricEncryption symmetricEncryption;

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME_IN_MS;

    @Autowired
    public JwtRefreshTokenServiceImpl(JwtRefreshTokenDao jwtRefreshTokenDao, UserDao userDao, JwtProviderImpl jwtProvider, SymmetricEncryption symmetricEncryption) {
        this.jwtRefreshTokenDao = jwtRefreshTokenDao;
        this.userDao = userDao;
        this.jwtProvider = jwtProvider;
        this.symmetricEncryption = symmetricEncryption;
    }

    @Override
    public JwtRefreshToken createRefreshToken(Long userId) {
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();

        jwtRefreshToken.setTokenId(UUID.randomUUID().toString());
        jwtRefreshToken.setUserId(userId);
        jwtRefreshToken.setCreationDate(LocalDateTime.now());
        jwtRefreshToken.setExpirationDate(LocalDateTime.now().plus(REFRESH_TOKEN_EXPIRATION_TIME_IN_MS, ChronoUnit.MILLIS));

        return jwtRefreshTokenDao.save(jwtRefreshToken);
    }

    @Override
    public User generateAccessTokenFromRefreshToken(String refreshTokenId) {
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenDao.findById(refreshTokenId).orElseThrow();

        if (jwtRefreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The refresh token expiration time was expired, invalid token");
        }

        User user = userDao.findById(jwtRefreshToken.getUserId()).orElseThrow();

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthority(user.getRole().name())))
                .build();

        String accessToken = jwtProvider.generateToken(userPrincipal);
//        user.setAccessToken(accessToken);
//        user.setRefreshToken(refreshTokenId);

        return user;

    }

    @Override
    public List<JwtRefreshToken> getRefreshTokens(Long id) {
        return jwtRefreshTokenDao.findByUserId(id).orElseThrow();
    }

    @Override
    public List<String> getInvalidRefreshToken(List<JwtRefreshToken> refreshTokens) {
        if (refreshTokens == null) {
            return null;
        }
        List<String> inValidRefreshToken = new ArrayList<>();
        for (JwtRefreshToken refreshToken : refreshTokens) {
            if (refreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
                inValidRefreshToken.add(refreshToken.getTokenId());
            }
        }

        return inValidRefreshToken;
    }

    @Override
    public List<String> getValidRefreshToken(List<JwtRefreshToken> refreshTokens) {
        if (refreshTokens == null) {
            return null;
        }
        List<String> validRefreshToken = new ArrayList<>();
        for (JwtRefreshToken refreshToken : refreshTokens) {
            if (!refreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
                validRefreshToken.add(refreshToken.getTokenId());
            }
        }

        return validRefreshToken;
    }

    @Override
    public void deleteInvalidRefreshTokens(List<String> invalidRefreshTokens) {
        jwtRefreshTokenDao.deleteAllById(invalidRefreshTokens);
    }
}
