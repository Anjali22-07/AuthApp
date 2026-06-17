package com.lcp.auth.auth.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcp.auth.auth.entities.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepositories extends JpaRepository<User, UUID> {


    //custom methods
    Optional<User> findByEmail(String email);
}
