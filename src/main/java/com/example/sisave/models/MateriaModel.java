package com.example.sisave.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="materia")
public class MateriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="materia_id")
    private Long id;
    @Column(name="nome", nullable = false)
    private String nome;
    @Column(name="media")
    private BigDecimal media;
    @ManyToOne
    @JoinColumn(name="curso_id")
    private CursoModel curso;
}

