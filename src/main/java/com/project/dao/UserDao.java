package com.project.dao;

import com.project.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserById(String id);

    void addUser(User user);

    void removeUser(String id);

    void updateUser(User user);
    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers(Map<String, String[]> paramMap);

    int getNumberOfUser(Map<String, String[]> paramMap);
}
