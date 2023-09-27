package com.example.sisave.models;

import com.example.sisave.models.enums.Poketype;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="pokemon")
public class PokemonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pokemon_id")
    private Long pokemonId;

    @Enumerated(EnumType.STRING)
    @Column(name="first_type", nullable = false)
    private Poketype firstType;

    @Enumerated(EnumType.STRING)
    @Column(name="second_type", nullable = true)
    private Poketype secondType;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="level")
    private Integer level;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="pix_key")
    private String pixKey;
}
