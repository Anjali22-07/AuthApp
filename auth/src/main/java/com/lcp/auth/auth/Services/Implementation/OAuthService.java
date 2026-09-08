package com.lcp.auth.auth.Services.Implementation;

import java.util.Optional;

import org.springframework.stereotype.Component;
import com.lcp.auth.auth.Repository.UserRepositories;
import com.lcp.auth.auth.entities.Provider;
import com.lcp.auth.auth.entities.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepositories userRepositories;

    public User processUser(String email, String name, String picture, Provider provider){
       
           Optional<User> existingUser= userRepositories.findByEmail(email);
           if(existingUser.isPresent()){
           return existingUser.get();
        }
         User user= new User();
         user.setEnabled(true);
         user.setEmail(email);
         user.setImg(picture);
         user.setName(name);
         user.setProvider(provider);
         
         return userRepositories.save(user);
        

    }

}
