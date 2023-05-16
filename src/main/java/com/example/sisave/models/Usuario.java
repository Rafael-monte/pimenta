package com.example.sisave.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
@Data
@Entity
@Table(name="usuario")
public class Usuario {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="username")
    private String username;

    @Column(name="email", length = 120, unique = true)
    private String email;

    @Column(name="secret")
    private String secret;
}
