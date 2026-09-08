package com.lcp.auth.auth.Security;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.lcp.auth.auth.Repository.RefreshTokenRepository;
import com.lcp.auth.auth.Services.Implementation.OAuthService;
import com.lcp.auth.auth.entities.Provider;
import com.lcp.auth.auth.entities.RefreshToken;
import com.lcp.auth.auth.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//This class implements Authentication success Handler
@Component
@RequiredArgsConstructor
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuthService oAuthService;
    private final JwtSecurity jwtSecurity;
    private final RefreshTokenRepository refreshTokenRepository; 
    private final CookieService cookieService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
       // we first need to identify the provider
       var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
       //providerId helps us Identify the provider used to login 
       String providerID= oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        //since return type of getPrincipal is mostly object or API/context, something represented through the authentication abstraction that is why we use typecasting here 
        var oAuthUser= (DefaultOAuth2User)authentication.getPrincipal();
        User user=null;
       if(providerID.equalsIgnoreCase("google")){    
        //extracting the values from oAuthUser 
          String email= oAuthUser.getAttribute("email").toString();
          String name= oAuthUser.getAttribute("name").toString();
          String img= oAuthUser.getAttribute("picture").toString();
           user=oAuthService.processUser(email, name, img, Provider.GOOGLE);
       }else if(providerID.equalsIgnoreCase("github")){
        String email= oAuthUser.getAttribute("email")!=null ? oAuthUser.getAttribute("email").toString():oAuthUser.getAttribute("login").toString()+"@github.com";
        String picture=oAuthUser.getAttribute("avatar_url").toString();
        String name= oAuthUser.getAttribute("login").toString();
         user= oAuthService.processUser(email, name, picture,Provider.GITHUB);
    }
    //Generating JWT 
       String jti= UUID.randomUUID().toString();

       RefreshToken refreshTokenEntity= RefreshToken.builder().
                    jti(jti)
                    .user(user)
                    .createdAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(jwtSecurity.getRefreshTokenTTLS()))
                    .revoked(false)
                    .build();

        refreshTokenRepository.save(refreshTokenEntity);

     String accessToken= jwtSecurity.generateAccessToken(user);
     String refreshToken= jwtSecurity.generateRefreshToken(user, jti);

     //adding refresh token in cookies

      cookieService.addRefreshCookie(response, refreshToken, (int)jwtSecurity.getRefreshTokenTTLS());
      cookieService.addNoStroreHeaders(response);
      
    }

}
