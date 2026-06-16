package com.lcp.auth.auth.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    private UUID id= UUID.randomUUID();
    private String role;
}
