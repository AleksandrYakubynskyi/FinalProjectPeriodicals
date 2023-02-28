package com.project.service;

import com.project.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    void removeUser(String id);
    void updateUser(User user);
    int getNumberOfUser(Map<String, String[]> paramMap);
    List<User> getAllUsers(Map<String, String[]> paramMap);
    Optional<User> getUserById(String id);
    Optional<User> getUserByEmail(String email);
}
