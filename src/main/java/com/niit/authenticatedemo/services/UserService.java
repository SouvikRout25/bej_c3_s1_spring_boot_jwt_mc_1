package com.niit.authenticatedemo.services;

import com.niit.authenticatedemo.domain.User;
import com.niit.authenticatedemo.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    public User addUser(User user);
    public User findByUsernameAndPassword(String username , String password) throws UserNotFoundException;
    List<User> getAllUsers();

    boolean deleteUser(int userid) throws UserNotFoundException;
}