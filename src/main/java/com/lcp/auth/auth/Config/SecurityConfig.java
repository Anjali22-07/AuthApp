package com.lcp.auth.auth.Config;

import com.lcp.auth.auth.AuthApplication;
import com.lcp.auth.auth.Security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthApplication authApplication;
   

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http.csrf(AbstractHttpConfigurer::disable);
    http.cors(Customizer.withDefaults())
        .authorizeHttpRequests(authorizeHttpRequest->{
            authorizeHttpRequest.requestMatchers("/api/V1/auth/register").permitAll()
            .requestMatchers("/api/V1/auth/login").permitAll()
            .anyRequest().authenticated();
    }).exceptionHandling(ex->ex.authenticationEntryPoint((request, response, e)->{
               System.out.println("Exception Gandling enabled");
                e.printStackTrace();
                response.setStatus(401);
                response.setContentType("application/Json");
                String message="Unauthorized access!"+e.getMessage();
                Map<String, String> errorMap= Map.of(
                    "message", message,
                    "status",String.valueOf(401)
                );
                var ObjectMapper= new ObjectMapper();
                response.getWriter().write(ObjectMapper.writeValueAsString(errorMap));
    })).addFilterBefore(jwtAuthenticationFilter ,UsernamePasswordAuthenticationFilter.class);
    
        return http.build();
    }

}
