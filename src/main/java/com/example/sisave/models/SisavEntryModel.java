package com.example.sisave.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="sisav_entry")
public class SisavEntryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="entry_id")
    private Long id;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @ManyToOne
    @JoinColumn(name="user_id")
    private Usuario usuario;
}
