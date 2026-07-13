package com.lcp.auth.auth.entities;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
 @Index(name= "refreshtokenjti_idx", columnList="jti"),
 @Index(name= "refeshtokeUserid_idx", columnList="userid")
})
public class RefreshToken {

    @Id
    @GeneratedValue(strategy =GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false, updatable = false)
    private String jti;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @Column(nullable = false,updatable = false)
    private Instant createdAt;
    @Column(nullable = false,updatable = false)
    private Instant updatedAt;
    @Column(nullable = false)
    private boolean revoked;
    //  @Column(nullable = false)
    // private String replaceByToken;



}
