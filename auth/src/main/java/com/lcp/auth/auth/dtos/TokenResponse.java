package com.lcp.auth.auth.dtos;

public record TokenResponse(String  accessToken, long expiresIn, String tokenType, UserDto userDto) {


     public static TokenResponse of(String  accessToken, long expiresIn, UserDto userDto){
         return new TokenResponse(accessToken, expiresIn, "Bearer",  userDto);
     }
}
