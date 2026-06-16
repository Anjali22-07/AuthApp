package com.lcp.auth.auth.entities;

import java.time.Instant;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    
    @Id
    private UUID id;
    private String name;
    @Column(unique= true, length=300)
    private String email;
    private String password;
    private String img;
    private boolean enabled= true;
    private Instant createdAt= Instant.now();
    private Instant updatedAt=Instant.now();
    @Enumerated(value=EnumType.STRING)    //to store the enum value as string in DB
    private Provider provider=Provider.LOCAL;
    
   // private Set<Role> role= new HashSet<>();

}
