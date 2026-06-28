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
        
         
          System.out.println("Total users: " + userRepo.count());

    userRepo.findAll().forEach(
        u -> System.out.println("DB Email: " + u.getEmail())
    );

    System.out.println("Searching for: " + username);


        User user=userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User with this email does not Exist"));
            System.out.println(user);

            System.out.println("FOUND USER = " + user.getEmail());
System.out.println("PASSWORD IN DB = " + user.getPassword());

            return user;
    }

}
