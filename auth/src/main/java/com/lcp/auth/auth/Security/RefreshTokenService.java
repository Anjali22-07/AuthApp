package com.lcp.auth.auth.Security;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lcp.auth.auth.dtos.RefreshTokenRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {
 
     private final CookieService cookieService;

    public Optional<String> readRefreshTokenRequest(RefreshTokenRequest body, HttpServletRequest request){

        if(request.getCookies()!=null){
            Optional<String> fromCookie= Arrays.stream(request.getCookies())
                                         .filter(c-> cookieService.getRefreshTokenName().equals(c.getName()))
                                         .map(Cookie::getValue)
                                         .filter(v-> !v.isBlank())
                                         .findFirst();
         
            if(fromCookie.isPresent()){
                 return fromCookie;
            }
        }

        if(body!=null && body.refreshToken()!=null && !body.refreshToken().isBlank()){
            return Optional.of(body.refreshToken());
        }

        return Optional.empty();        
    }

}
