package com.lcp.auth.auth.Services.Implementation;

import java.time.Instant;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.lcp.auth.auth.Exceptions.ResourceNotFoundException;
import com.lcp.auth.auth.Repository.UserRepositories;
import com.lcp.auth.auth.Services.UserService;
import com.lcp.auth.auth.dtos.UserDto;
import com.lcp.auth.auth.entities.User;
import com.lcp.auth.auth.helper.UserHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService{

    private final UserRepositories userRepo;
    private final ModelMapper modelMapper;
    private final UserHelper userHelper;
    

   

    @Override
    @Transactional
    public UserDto createUser(UserDto userdto) {
        
        if(userdto.getEmail()==null || userdto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email Id Required");
        }

        if(userRepo.existsByEmail(userdto.getEmail())){
            throw new IllegalArgumentException("Email Id already Exists");
        }

        //converting from dto to entity
        User user=modelMapper.map(userdto,User.class);  //this will map the data from dto to db
        //assign role here for authorization     
        User savedUser= userRepo.save(user);
            return modelMapper.map(savedUser, UserDto.class);
    }




    @Override
    public UserDto findUserByEmail(String email) {
       
    User user= userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
    return modelMapper.map(user, UserDto.class);   
}





    @Override
    public UserDto updateUser(String Id, UserDto user) {

        UUID uId= userHelper.parseUserId(Id);

         User user1= userRepo.findById(uId)
        .orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        user1.setImg(user.getImg());
        user1.setProvider(user.getProvider());
        user1.setUpdatedAt(Instant.now());

       User savedUser= userRepo.save(user1);
       return modelMapper.map(savedUser, UserDto.class);
    }




    @Override
    public void deleteUser(String email) {
         User user= userRepo.findByEmail(email).orElseThrow(()->new  ResourceNotFoundException("User Does not Exist"));
         userRepo.delete(user);
      }


    @Override
    public Boolean ifUserExistByEmail(String email) {

        User user=userRepo.findByEmail(email).orElseThrow(null);
        return (user!=null)? true: false;
           }




    @Override
    public Iterable<UserDto> getAllUsers() {
        
         return userRepo
         .findAll()
         .stream()
         .map(user-> modelMapper.map(user, UserDto.class))
         .toList();
    }

}
