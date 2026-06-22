package com.lcp.auth.auth.dtos;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.lcp.auth.auth.entities.Provider;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private String id;
    @NotBlank(message ="Name cannot be empty")
    private String name;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message ="Password cannot be empty")
    @Size(min=8, message="Password must have atleast 8 characters")
    private String password;
    private String img;
    private boolean enabled= true;
    private Instant createdAt= Instant.now();
    private Instant updatedAt=Instant.now();
    private Provider provider=Provider.LOCAL;
    private Set<RoleDto> role= new HashSet<>();

}
