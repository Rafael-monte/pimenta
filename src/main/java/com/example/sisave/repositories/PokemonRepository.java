package com.example.sisave.repositories;

import com.example.sisave.models.PokemonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<PokemonModel, Long> {
    List<PokemonModel> findAllByCreatedByIsNot(String createdBy);
    List<PokemonModel> findAllByCreatedByIs(String createdBy);
}
