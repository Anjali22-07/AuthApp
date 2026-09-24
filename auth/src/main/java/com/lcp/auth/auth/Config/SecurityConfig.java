package com.lcp.auth.auth.Config;

import com.lcp.auth.auth.AuthApplication;
import com.lcp.auth.auth.Security.JwtAuthenticationFilter;
import com.lcp.auth.auth.Security.OAuthAuthenticationSuccessHandler;
import io.jsonwebtoken.lang.Arrays;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthApplication authApplication;
    private final OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;
   

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
             .requestMatchers("/api/V1/auth/refresh").permitAll()
            .requestMatchers("/api/V1/auth/logout").permitAll()
            .anyRequest().authenticated();
    }).oauth2Login(oauth2 -> oauth2.successHandler(oAuthAuthenticationSuccessHandler))
    .exceptionHandling(ex->ex.authenticationEntryPoint((request, response, e)->{
               System.out.println("Exception Handling enabled");
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

       //  String[] urls= corsURL.trim().split(",");

         var config= new CorsConfiguration();
         config.setAllowedOrigins(List.of("http://localhost:5173"));
         config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","HEAD", "OPTIONS"));
         config.setAllowedHeaders(List.of("*"));
         config.setAllowCredentials(true);

         var source= new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**", config);
         return source;

    }
}
