package com.modern_inf.management.service.userService;

import com.modern_inf.management.model.asana.Asana;
import com.modern_inf.management.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User setPersonalAccessTokenForAsana(User user);

    User setPersonalAccessTokenForGitlab(User user);

    Optional<User> findByUsername(String username);

    List<User> findAllUser();

    Optional<User> findUserById(Long userId);

    Asana findAsanaAccountByUser(Long userId);
}
