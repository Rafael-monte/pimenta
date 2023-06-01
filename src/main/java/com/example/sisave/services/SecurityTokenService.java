package com.example.sisave.services;
import com.example.sisave.models.auth.SecurityToken;
import com.example.sisave.repositories.SecurityTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SecurityTokenService {
    @Autowired
    private SecurityTokenRepository repository;


    @Transactional
    public Boolean needToUpdateToken() {
        final long ONE_DAY_IN_HOURS=24L;
        SecurityToken lastToken = this.repository.getTop1ByOrderByLastUpdateDesc();
        if (lastToken == null) {
            lastToken = createNewToken();
            this.repository.saveAndFlush(lastToken);
            return Boolean.FALSE;
        }
        LocalDateTime lastTokenUpdate = lastToken.getLastUpdate();
        LocalDateTime todayDate = LocalDateTime.now();
        Duration intervalBetweenDates = Duration.between(lastTokenUpdate, todayDate);
        return intervalBetweenDates.toHours() > ONE_DAY_IN_HOURS;
    }

    private SecurityToken createNewToken() {
        String newIntegrationToken = UUID.randomUUID().toString();
        SecurityToken token = new SecurityToken();
        token.setIntegrationToken(newIntegrationToken);
        token.setLastUpdate(LocalDateTime.now());
        return token;
    }

    @Transactional
    public void updateToken() {
        Long lastTokenId = this.repository.getTop1ByOrderByLastUpdateDesc().getId();
        SecurityToken token = createNewToken();
        token.setId(lastTokenId);
        this.repository.saveAndFlush(token);
    }


    @Transactional
    public String getToken() {
        SecurityToken lastToken = this.repository.getTop1ByOrderByLastUpdateDesc();
        return lastToken.getIntegrationToken();
    }
}
