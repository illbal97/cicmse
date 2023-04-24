package com.modern_inf.management.service.authentication;

import com.modern_inf.management.model.User;
import com.modern_inf.management.security.UserPrincipal;

public interface AuthenticationService {

    UserPrincipal signIn(User signUser);
    String setUpRefreshTokenAndReturn(Long userId);
}
