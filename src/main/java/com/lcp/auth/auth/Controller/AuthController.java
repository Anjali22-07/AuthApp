package com.lcp.auth.auth.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lcp.auth.auth.Repository.UserRepositories;
import com.lcp.auth.auth.Security.JwtSecurity;
import com.lcp.auth.auth.Services.Implementation.AuthServiceImplementation;
import com.lcp.auth.auth.dtos.LoginRequest;
import com.lcp.auth.auth.dtos.TokenResponse;
import com.lcp.auth.auth.dtos.UserDto;
import com.lcp.auth.auth.entities.User;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/V1/auth")
@AllArgsConstructor
public class AuthController {

        private final AuthServiceImplementation authImp;
        private final AuthenticationManager authenticationManager;
        private final UserRepositories userRepo;
        private final JwtSecurity jwtSecurity;
        private final ModelMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(authImp.registerUser(userDto));
}

//Authenticating user and generating Jwt Token 

     @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginRequest loginRequest){
   
         //step 1: Authentication 
         Authentication authentication = authenticate(loginRequest);
         User user= userRepo.findByEmail(loginRequest.email()).orElseThrow(()-> new BadCredentialsException("Invalid UserName or Password"));
        if(!user.isEnabled())  throw new DisabledException("user not Enaled");
        //step 2: Generate Token
          String accessToken= jwtSecurity.generateAccessToken(user);
          TokenResponse tokenResponse= TokenResponse.of(accessToken, " ", jwtSecurity.getAccessTokenTTLS(), mapper.map(user, UserDto.class));
          return ResponseEntity.ok(tokenResponse);
    }

    private Authentication authenticate(LoginRequest loginRequest){
            try{
                //AuthenticationManager actually performs Authentication
                // UsernamePasswordAuthenticationToken(loginRequest.Email(), loginRequest.password()-- passes the username and password entered by the user
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
            }catch(Exception e){
                throw new BadCredentialsException("Invalid UserName and Password");
            }
    }

}
