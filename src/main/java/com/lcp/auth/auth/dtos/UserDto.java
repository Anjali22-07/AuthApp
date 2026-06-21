package com.lcp.auth.auth.dtos;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.lcp.auth.auth.entities.Provider;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private String img;
    private boolean enabled= true;
    private Instant createdAt= Instant.now();
    private Instant updatedAt=Instant.now();
    private Provider provider=Provider.LOCAL;
    private Set<RoleDto> role= new HashSet<>();

}
