package com.civiclens.backend.service;

import com.civiclens.backend.model.User;

public interface UserService {
    User registerUser(User user);
    User getUserByEmail(String email);
    User loginUser(String email, String password);
}
