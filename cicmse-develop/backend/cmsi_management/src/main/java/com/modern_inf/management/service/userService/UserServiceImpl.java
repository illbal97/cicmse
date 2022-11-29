package com.modern_inf.management.service.userService;

import com.modern_inf.management.model.asana.Asana;
import com.modern_inf.management.model.User;
import com.modern_inf.management.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setUsername(user.getUsername());
        user.setName(user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userDao.save(user);
    }

    @Override
    public User setPersonalAccessTokenForAsana(User user) {
        var u = this.userDao.findById(user.getId());
        u.get().setAsanaPersonalAccessToken(user.getAsanaPersonalAccessToken());

        return userDao.save(u.get());
    }

    @Override
    public User setPersonalAccessTokenForGitlab(User user) {
        var u = this.userDao.findById(user.getId());
        u.get().setGitlabPersonalAccessToken(user.getGitlabPersonalAccessToken());

        return userDao.save(u.get());
    }

    @Override
    public Optional<User> findByUsername(String username) {

        return userDao.findByUsername(username);
    }

    @Override
    public List<User> findAllUser() {

        return userDao.findAll();
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public Asana findAsanaAccountByUser(Long userId) {
        Optional<User> user = this.userDao.findById(userId);
        return user.map(User::getAsanaAccount).orElse(null);
    }

    @Override
    public User setAccessKeyAndSecretAccessKeyForAws(User user) {
        var u = this.userDao.findById(user.getId());
        u.get().setAwsAccessSecretKey(user.getAwsAccessSecretKey());
        u.get().setAwsAccessSecretKey(user.getAwsAccessSecretKey());

        return userDao.save(u.get());
    }



}
