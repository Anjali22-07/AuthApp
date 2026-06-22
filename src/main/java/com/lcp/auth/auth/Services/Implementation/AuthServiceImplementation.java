package com.lcp.auth.auth.Services.Implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lcp.auth.auth.Services.AuthServices;
import com.lcp.auth.auth.dtos.UserDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImplementation implements AuthServices{

    private final UserServiceImplementation userImpl;

    @Override
    public UserDto registerUser(UserDto userDto) {

        //verify email logic
        
        UserDto userDto1= userImpl.createUser(userDto);

        return userDto1;
      }

    @Override
    public UserDto loginUser(UserDto userDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loginUser'");
    }




}
