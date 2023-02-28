package com.project.service;

import com.project.dao.UserDao;
import com.project.entity.User;
import com.project.entity.enums.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DefaultUserService implements UserService {
    private final UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void addUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setRole(Role.USER);
        user.setBalance(BigDecimal.ZERO);
        userDao.addUser(user);
    }

    @Override
    public void removeUser(String id) {
        userDao.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public int getNumberOfUser(Map<String, String[]> paramMap) {
        return userDao.getNumberOfUser(paramMap);
    }

    @Override
    public List<User> getAllUsers(Map<String, String[]> paramMap) {
        return userDao.getAllUsers(paramMap);
    }
}
