package com.example.sisave.models.auth;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="security_token")
public class SecurityToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_token")
    private Long id;

    @Column(name="integration_token")
    private String integrationToken;

    @Column(name="last_update")
    private LocalDateTime lastUpdate;
}
