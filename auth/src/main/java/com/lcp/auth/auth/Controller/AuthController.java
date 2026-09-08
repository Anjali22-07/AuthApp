package com.lcp.auth.auth.Controller;


import java.time.Instant;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lcp.auth.auth.Repository.RefreshTokenRepository;
import com.lcp.auth.auth.Repository.UserRepositories;
import com.lcp.auth.auth.Security.CookieService;
import com.lcp.auth.auth.Security.JwtSecurity;
import com.lcp.auth.auth.Security.RefreshTokenService;
import com.lcp.auth.auth.Services.Implementation.AuthServiceImplementation;
import com.lcp.auth.auth.dtos.LoginRequest;
import com.lcp.auth.auth.dtos.RefreshTokenRequest;
import com.lcp.auth.auth.dtos.TokenResponse;
import com.lcp.auth.auth.dtos.UserDto;
import com.lcp.auth.auth.entities.RefreshToken;
import com.lcp.auth.auth.entities.User;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        private final RefreshTokenRepository refreshTokenRepository;
        private final CookieService cookieService;
        private final RefreshTokenService  refreshTokenService;

       

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(authImp.registerUser(userDto));
}

//Authenticating user and generating Jwt Token 


     @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
   
        System.out.println("Login API called");
         //step 1: Authentication 
         Authentication authentication = authenticate(loginRequest);
         User user= userRepo.findByEmail(loginRequest.email()).orElseThrow(()-> new BadCredentialsException("Invalid UserName or Password"));
        if(!user.isEnabled())  throw new DisabledException("user not Enabled");

       //storing the values in refreshToken 
       String jti= UUID.randomUUID().toString();
       var refreshToken01= RefreshToken.builder()
                          .jti(jti)
                          .user(user)
                          .createdAt(Instant.now())
                          .expiresAt(Instant.now().plusSeconds(jwtSecurity.getRefreshTokenTTLS()))
                          .revoked(false)
                          .build();
    
        //saving refreshToken information in DB
         
         refreshTokenRepository.save(refreshToken01);
                         

          
        //step 2: Generate Token
          String accessToken= jwtSecurity.generateAccessToken(user);
          String refreshToken= jwtSecurity.generateRefreshToken(user, refreshToken01.getJti());
          //use cookie service to attach refresh token to cookie
               
           cookieService.addRefreshCookie(response, refreshToken, (int)jwtSecurity.getRefreshTokenTTLS());
           cookieService.addNoStroreHeaders(response);

          TokenResponse tokenResponse= TokenResponse.of(accessToken, refreshToken, jwtSecurity.getAccessTokenTTLS(), mapper.map(user, UserDto.class));
          return ResponseEntity.ok(tokenResponse);
    }


      //create a new end point -- to renew access Token and refreshToken 
      @PostMapping("/refresh")
         public ResponseEntity<TokenResponse> newRefreshToken(
             @RequestBody(required=false) RefreshTokenRequest body,
             HttpServletRequest request,
             HttpServletResponse response){

                String refreshToken = refreshTokenService.readRefreshTokenRequest(body,request).orElseThrow(()->new BadCredentialsException("Cookie does not exist"));

                 if(!jwtSecurity.isRefreshToken(refreshToken)){
                    throw new BadCredentialsException("Invalid Refresh Token");
                 }

                String jti=jwtSecurity.getJti(refreshToken);
                UUID userId= UUID.fromString(jwtSecurity.getSubject(refreshToken));

               RefreshToken storedRefreshToken= refreshTokenRepository.findByJti(jti).orElseThrow(()-> new BadCredentialsException("The refresh token doesn't exist"));

                if(storedRefreshToken.isRevoked()){
                    throw new BadCredentialsException("RefreshToken already Revoked");
                }

                if(storedRefreshToken.getExpiresAt().isBefore(Instant.now())){
                    throw new BadCredentialsException("Token already Expired");
                }

                if(!storedRefreshToken.getUser().getId().equals(userId)){
                    throw new BadCredentialsException("Token not found");
                }

                //rotating refreshToken 

                storedRefreshToken.setRevoked(true);
                String newJti= UUID.randomUUID().toString();
                storedRefreshToken.setReplaceByToken(newJti);
                refreshTokenRepository.save(storedRefreshToken);

                User user= storedRefreshToken.getUser();

                var refreshToken0b1= RefreshToken.builder() 
                                     .jti(newJti)
                                     .user(user)
                                    .createdAt(Instant.now())
                                    .expiresAt(Instant.now().plusSeconds(jwtSecurity.getRefreshTokenTTLS()))
                                    .revoked(false)
                                    .build();

                refreshTokenRepository.save(refreshToken0b1);

         //renewing accessToken and AccessToken
         String newAccessToken = jwtSecurity.generateAccessToken(user);
          String newRefreshToken= jwtSecurity.generateRefreshToken(user, refreshToken0b1.getJti());
          //use cookie service to attach refresh token to cookie
               
           cookieService.addRefreshCookie(response, newRefreshToken, (int)jwtSecurity.getRefreshTokenTTLS());
           cookieService.addNoStroreHeaders(response);

          TokenResponse tokenResponse= TokenResponse.of(newAccessToken, newRefreshToken, jwtSecurity.getAccessTokenTTLS(), mapper.map(user, UserDto.class));
          return ResponseEntity.ok(tokenResponse);

     }

      
       @PostMapping("/logout")
        public ResponseEntity<Void> logOut(HttpServletRequest request, HttpServletResponse response){

            refreshTokenService.readRefreshTokenRequest(null, request).ifPresent(token->{
                try{
                     if(jwtSecurity.isRefreshToken(token)){
                         String jti= jwtSecurity.getJti(token);
                         refreshTokenRepository.findByJti(jti).ifPresent(
                              rt->{
                                 rt.setRevoked(true);
                                 refreshTokenRepository.save(rt);
                             }
                         );
                     }
                        
                }catch(JwtException Ignored){

                }
            });

           //clearing the cookies 
             cookieService.clearRefreshCookie(response);
             cookieService.addNoStroreHeaders(response);
             SecurityContextHolder.clearContext();
            
          return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

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