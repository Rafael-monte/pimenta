package com.example.sisave.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="curso")
@Table(name="curso")
public class CursoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="curso_id")
    private Long id;

    @Column(name="nome")
    private String nome;

    @jakarta.persistence.ManyToOne
    @JoinColumn(name="user_id")
    private Usuario usuario;

}
