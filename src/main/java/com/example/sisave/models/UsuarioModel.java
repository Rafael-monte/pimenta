package com.example.sisave.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="username")
    private String username;

    @Column(name="email", length = 120, unique = true)
    private String email;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="secret")
    private String secret;
}
