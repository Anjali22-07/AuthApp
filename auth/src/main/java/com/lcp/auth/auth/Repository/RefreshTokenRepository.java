package com.lcp.auth.auth.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lcp.auth.auth.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,UUID>{

    public Optional<RefreshToken> findByJti(String jti);

}
