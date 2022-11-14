package com.modern_inf.management.controller;

import com.modern_inf.management.model.User;
import com.modern_inf.management.service.authentication.AuthenticationService;
import com.modern_inf.management.service.jsonWebToken.JwtRefreshTokenService;
import com.modern_inf.management.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authentication")
public class AuthenticationController {


    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final JwtRefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationController(UserService userService,
                                    AuthenticationService authenticationService,
                                    JwtRefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
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
        return new ResponseEntity<>(authenticationService.signIn(user), HttpStatus.OK);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> generateRefreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(refreshTokenService.generateAccessTokenFromRefreshToken(refreshToken));
    }
}
