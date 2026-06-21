package com.lcp.auth.auth.Controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcp.auth.auth.Services.UserService;
import com.lcp.auth.auth.Services.Implementation.UserServiceImplementation;
import com.lcp.auth.auth.dtos.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/V1/Users")
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
       return ResponseEntity.status(201).body(userService.createUser(userDto));
    }  

    @GetMapping()
    public ResponseEntity<Iterable<UserDto>> getUsers(){
       return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){

      return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @PutMapping("Id/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userdto) {
        return ResponseEntity.ok(userService.updateUser(id, userdto));
    }

    
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email){
         userService.deleteUser(email);
         return ResponseEntity.noContent().build();
    }
   
    
}
