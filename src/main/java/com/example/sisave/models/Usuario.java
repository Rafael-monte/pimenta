package com.example.sisave.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
    @JsonIgnore()
    private Long userId;

    @JsonProperty("username")
    @Column(name="username")
    private String username;

    @JsonProperty("email")
    @Column(name="email", length = 120, unique = true)
    private String email;

    @JsonProperty("secret")
    @Column(name="secret")
    private String secret;
}
