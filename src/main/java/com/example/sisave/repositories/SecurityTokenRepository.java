package com.example.sisave.repositories;

import com.example.sisave.models.auth.SecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityTokenRepository extends JpaRepository<SecurityToken, Long> {
    public SecurityToken getTop1ByOrderByLastUpdateDesc();
}
