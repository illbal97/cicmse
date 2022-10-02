package com.modern_inf.management.service;

import com.modern_inf.management.model.User;

public interface AuthenticationService {

    User signIn(User signUser);
}
