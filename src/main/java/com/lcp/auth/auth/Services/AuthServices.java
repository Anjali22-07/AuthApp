package com.lcp.auth.auth.Services;



import com.lcp.auth.auth.dtos.UserDto;

public interface AuthServices {

    public UserDto registerUser(UserDto userDto);
    public UserDto loginUser(UserDto userDto);

}
