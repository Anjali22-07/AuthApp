package com.lcp.auth.auth.Security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.lcp.auth.auth.entities.Role;
import com.lcp.auth.auth.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class JwtSecurity {

    private final SecretKey secretKey;
    private final long accessTokenTTLS;
    private final long refreshTokenTTLS;
    private final String issuer;

    public JwtSecurity(@Value("${security.jwt.secret}")String secretKey, @Value("${security.jwt.access-ttl-seconds}")long accessTokenTTLS, @Value("${security.jwt.refresh-ttl-seconds}")long refreshTokenTTLS, @Value("${security.jwt.issuer}") String issuer) {
        
        this.accessTokenTTLS = accessTokenTTLS;
        this.refreshTokenTTLS = refreshTokenTTLS;
        this.issuer = issuer;

        if(secretKey==null || secretKey.length()<64){
           throw new IllegalArgumentException("Invalid Secret Key");
        }

        this.secretKey= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //method for Generating Access Token

    public String generateAccessToken(User user){
        Instant now= Instant.now();
        List<String> role= user.getRole()==null? List.of(): user.getRole().stream().map(Role::getRole).toList();

         return Jwts.builder()           //Creates a new JWT builder object.You're starting to construct a token.
         .id(UUID.randomUUID().toString())    //Gives every token a unique identifier.Useful if you want to blacklist/revoke a specific token later.
         .subject(user.getId().toString())    
         .issuer(issuer)
         .issuedAt(Date.from(now))
         .expiration(Date.from(now.plusSeconds(accessTokenTTLS)))
         .claims(Map.of(                        //Claims are pieces of information stored inside the JWT payload.
            "email", user.getEmail(),
            "role", role,
            "typ", "access"
         )).signWith(secretKey)          //Signs the token using your secret key.Prevents tampering.Withot signing the jwt token can be tampered easily
         .compact();                     //Converts everything into the final JWT string.
    }

    //Method for Generating Refresh Token 

      public String generateRefreshToken(User user){
        Instant now= Instant.now();
          return Jwts.builder()           //Creates a new JWT builder object.You're starting to construct a token.
         .id(UUID.randomUUID().toString())    //Gives every token a unique identifier.Useful if you want to blacklist/revoke a specific token later.
         .subject(user.getId().toString())    
         .issuer(issuer)
         .issuedAt(Date.from(now))
         .expiration(Date.from(now.plusSeconds(refreshTokenTTLS)))
         .claims(Map.of(                  //Claims are pieces of information stored inside the JWT payload.
             "typ", "refresh"
         )).signWith(secretKey)          //Signs the token using your secret key.Prevents tampering.Withot signing the jwt token can be tampered easily
         .compact();                     //Converts everything into the final JWT string.
    }

    //parse Token 
     public Jws<Claims> parseToken(String token){
        try{
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        }
        catch(JwtException e){
            throw e;
        }
     }

     public boolean isAccessToken(String Token){
         Claims c= parseToken(Token).getPayload();
         return "access".equals(c.get("typ"));
     }

      public boolean isRefreshToken(String Token){
         Claims c= parseToken(Token).getPayload();
         return "refresh".equals(c.get("typ"));
     }

     public String getJti(String token){          //jti is the id of the JWT token 
        return parseToken(token).getPayload().getId();
     }

}
