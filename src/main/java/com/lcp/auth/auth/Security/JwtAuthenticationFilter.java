package com.lcp.auth.auth.Security;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.lcp.auth.auth.Repository.UserRepositories;
import com.lcp.auth.auth.helper.UserHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

      private final JwtSecurity jwtSecurity;
      private final UserHelper userHelper;
      private final UserRepositories userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       
        //fetch token
        String header= request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            //extract token--> Validate token --> Authenticate--> set in security context

            String token= header.substring(7);  

            try{
                     // extracting token 
            Jws<Claims> parse= jwtSecurity.parseToken(token);
            if(!jwtSecurity.isAccessToken(token)){
                 filterChain.doFilter(request, response);
                 return;
            }
           Claims payload= parse.getPayload();
           String userId= payload.getSubject();
           UUID userUUID= userHelper.parseUserId(userId);

           //validating user 
           userRepo.findById(userUUID).ifPresent( user ->{
                 if(!user.isEnabled()){
                   return;
                }
                  List<GrantedAuthority> authorities= user.getRole()==null? List.of()
                   : user.getRole()
                   .stream()
                   .map(role-> (GrantedAuthority)new SimpleGrantedAuthority(role.getRole()))
                   .toList();
                 

                 //Authentication 
                //Spring Security stores an object of type Authentication in the SecurityContext.Very often, that object is a UsernamePasswordAuthenticationToken.
                 UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(user.getEmail(),null,authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 //setting in security context
                    if(SecurityContextHolder.getContext().getAuthentication()==null){
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
           
                });




            }catch(ExpiredJwtException e){
                e.printStackTrace();
            }catch(MalformedJwtException m){
                m.printStackTrace();
            }catch(JwtException J){
                J.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
                filterChain.doFilter(request, response);
    }

    //since we do not need to filter register or login ? because the purpose of the login endpoint is to create the token.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            return request.getRequestURI().startsWith("/auth/V1/auth");
    }

    
      

}
