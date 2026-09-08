package com.lcp.auth.auth.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lcp.auth.auth.dtos.UserDto;
import com.lcp.auth.auth.entities.User;


@Repository
public interface UserRepositories extends JpaRepository<User, UUID> {


    //custom methods
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
