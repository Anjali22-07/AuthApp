package com.lcp.auth.auth.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lcp.auth.auth.Services.Implementation.AuthServiceImplementation;
import com.lcp.auth.auth.dtos.UserDto;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/V1/auth")
@AllArgsConstructor
public class AuthController {

        private final AuthServiceImplementation authImp;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(authImp.registerUser(userDto));
}
}
