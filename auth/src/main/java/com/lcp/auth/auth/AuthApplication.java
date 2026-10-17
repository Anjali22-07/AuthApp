package com.lcp.auth.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lcp.auth.auth.Repository.RoleRepository;
import com.lcp.auth.auth.dtos.AppConstants;
import com.lcp.auth.auth.entities.Role;


@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

	@Autowired 
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//admin 
		roleRepository.findByRole("ROLE_"+AppConstants.ADMIN_ROLE).ifPresentOrElse(Role ->{
			System.out.println("Role Already exists");
		},()->{
                Role role=new Role();
				role.setRole("ROLE_"+AppConstants.ADMIN_ROLE);
				role.setId(UUID.randomUUID());
				roleRepository.save(role);
		});

		//user 
		roleRepository.findByRole("ROLE_"+AppConstants.GUEST_ROLE).ifPresentOrElse(Role ->{
			System.out.println("Role Already exists");
		},()->{
                Role role=new Role();
				role.setRole("ROLE_"+AppConstants.GUEST_ROLE);
				role.setId(UUID.randomUUID());
				roleRepository.save(role);
		});

		}
   
}
