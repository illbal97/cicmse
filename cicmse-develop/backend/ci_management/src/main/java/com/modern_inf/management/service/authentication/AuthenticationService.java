package com.modern_inf.management.service.authentication;

import com.modern_inf.management.model.User;
import com.modern_inf.management.security.UserPrincipal;

public interface AuthenticationService {

    UserPrincipal login(User signUser);
    String setUpRefreshTokenAndReturn(Long userId);
    void logOut(User requestUser);
}
