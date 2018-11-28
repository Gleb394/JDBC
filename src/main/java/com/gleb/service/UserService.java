package com.gleb.service;


import com.gleb.model.User;

public interface UserService {
    public User addUser(User user);
    public User findByEmail(String email);
    boolean validatePassword(User user, String password);

}
