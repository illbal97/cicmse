package com.modern_inf.management.controller;

import com.amazonaws.util.StringUtils;
import com.modern_inf.management.helper.CookieFactory;
import com.modern_inf.management.model.User;
import com.modern_inf.management.security.jwt.JwtProviderImpl;
import com.modern_inf.management.service.authentication.AuthenticationService;
import com.modern_inf.management.service.jsonWebToken.JwtRefreshTokenService;
import com.modern_inf.management.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/authentication")
public class AuthenticationController {


    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final JwtRefreshTokenService refreshTokenService;

    private final CookieFactory cookieFactory;

    private final JwtProviderImpl jwtProvider;

    @Autowired
    public AuthenticationController(UserService userService,
                                    AuthenticationService authenticationService,
                                    JwtRefreshTokenService refreshTokenService, CookieFactory cookieFactory, JwtProviderImpl jwtProvider) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.cookieFactory = cookieFactory;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        var userPrincipal = authenticationService.signIn(user);

        if (userPrincipal != null) {
            var refreshToken = authenticationService.setUpRefreshTokenAndReturn(userPrincipal.getUser().getId());

            if (!StringUtils.isNullOrEmpty(refreshToken)) {
                var jwtCookie = this.cookieFactory.createJwtTokenCookie(this.jwtProvider.generateToken(userPrincipal));
                var rfCookie = this.cookieFactory.createRefreshTokenCookie(refreshToken);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
                headers.add(HttpHeaders.SET_COOKIE, rfCookie.toString());
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(userPrincipal.getUser());
            }
            return ResponseEntity.ok((List.of("Refresh Token not exist")));
        }

        return ResponseEntity.ok(List.of("The user was not provided by the given credential"));
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> generateRefreshToken(@RequestParam Long userId) {
        var token = refreshTokenService.generateAccessTokenFromRefreshToken(userId);
        if (!StringUtils.isNullOrEmpty(token)) {
            var jwtCookie = this.cookieFactory.createJwtTokenCookie(token);
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
        }
        return ResponseEntity.ok((List.of("Refresh Token not exist or not valid")));
    }

    @PostMapping("logOut")
    public ResponseEntity<?> logOut(@RequestBody User user) {
        authenticationService.logOut(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, this.cookieFactory.deleteCookie("jwt_token").toString());
        headers.add(HttpHeaders.SET_COOKIE, this.cookieFactory.deleteCookie("rf_token").toString());

        return ResponseEntity.status(200).headers(headers).body(List.of("User successfully logout"));
    }
}
