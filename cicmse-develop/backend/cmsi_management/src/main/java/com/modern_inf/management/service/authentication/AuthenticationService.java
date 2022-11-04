package com.modern_inf.management.service.authentication;

import com.modern_inf.management.model.User;

public interface AuthenticationService {

    User signIn(User signUser);
}
