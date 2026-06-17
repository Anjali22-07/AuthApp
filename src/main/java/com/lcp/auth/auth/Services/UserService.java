package com.lcp.auth.auth.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lcp.auth.auth.entities.User;

public interface UserService {

    User saveUser(User user);
    Optional<User> findUserById(UUID Id);
    User updateUser(User user);
    void deleteUser(UUID Id);
    Boolean isUserExist(UUID Id);
    Boolean ifUserExistByEmail(String email);
    List<User> getAllUsers();



}
