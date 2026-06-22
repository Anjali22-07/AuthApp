package com.lcp.auth.auth.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lcp.auth.auth.dtos.UserDto;


public interface UserService {

    UserDto createUser(UserDto userdto);
    UserDto findUserByEmail(String email);
    UserDto updateUser(String Id, UserDto user);
    void deleteUser(String email);
    Boolean ifUserExistByEmail(String email);
    Iterable<UserDto> getAllUsers();



}
