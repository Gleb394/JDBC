package com.gleb.dao;

import com.gleb.model.User;

public interface UserDao {
    User addUser(User user);
    User findByEmail(String email);
    User findByToken(String token);
}
