package com.lcp.auth.auth.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lcp.auth.auth.Repository.UserRepositories;
import com.lcp.auth.auth.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {

    private final UserRepositories userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user=userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User with this email does not Exist"));
            return user;
    }

}
