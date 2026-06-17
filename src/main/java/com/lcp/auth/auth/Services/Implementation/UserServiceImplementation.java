package com.lcp.auth.auth.Services.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lcp.auth.auth.Exceptions.ResourceNotFoundException;
import com.lcp.auth.auth.Repository.UserRepositories;
import com.lcp.auth.auth.Services.UserService;
import com.lcp.auth.auth.entities.User;

@Service
public class UserServiceImplementation implements UserService{

    private final UserRepositories userRepo;

    public UserServiceImplementation(UserRepositories userRepo){
        this.userRepo=userRepo;
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public Optional<User> findUserById(UUID Id) {
        
        return userRepo.findById(Id);
    }

    @Override
    public User updateUser(User user) {
        User user1= userRepo.findById(user.getId())
        .orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setImg(user.getImg());
        user1.setProvider(user.getProvider());

       return  userRepo.save(user1);
        
    }

    @Override
    public void deleteUser(UUID Id) {
        User user1= userRepo.findById(Id)
        .orElseThrow(()->new ResourceNotFoundException("User Not Found"));

         userRepo.delete(user1);
        
    }

    @Override
    public Boolean isUserExist(UUID Id) {
        User user1= userRepo.findById(Id).orElse(null);
        return user1!=null?true:false;
    }

    @Override
    public Boolean ifUserExistByEmail(String email) {
          User user1= userRepo.findByEmail(email).orElse(null);
        return user1!=null?true:false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}
