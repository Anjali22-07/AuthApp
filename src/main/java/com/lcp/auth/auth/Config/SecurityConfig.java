package com.lcp.auth.auth.Config;

import com.lcp.auth.auth.AuthApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final AuthApplication authApplication;

    SecurityConfig(AuthApplication authApplication) {
        this.authApplication = authApplication;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeHttpRequest->{
            authorizeHttpRequest.requestMatchers("/api/V1/auth/register").permitAll()
            .requestMatchers("/api/V1/auth/login").permitAll()
            .anyRequest().authenticated();
    }).httpBasic(Customizer.withDefaults());
    
        return http.build();
    }

}
