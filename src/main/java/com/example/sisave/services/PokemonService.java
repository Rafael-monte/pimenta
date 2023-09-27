package com.example.sisave.services;

import com.example.sisave.exceptions.BadRequestBodyException;
import com.example.sisave.models.PokemonModel;
import com.example.sisave.models.dto.PokemonDto;
import com.example.sisave.repositories.PokemonRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository repository;

    public void save(PokemonModel pokemon) {
        checkFields(pokemon);
        repository.saveAndFlush(pokemon);
    }

    public List<PokemonDto> getAllPokemonsNotCreatedByCurrentUser(String createdBy) {
        return repository.findAllByCreatedByIsNot(createdBy)
                .stream()
                .map(poke -> new PokemonDto().wrap(poke)).collect(Collectors.toList());
    }

    public List<PokemonDto> getAllPokemonsCreatedByCurrentUser(String createdBy) {
        return repository.findAllByCreatedByIs(createdBy)
                .stream()
                .map(poke -> new PokemonDto().wrap(poke)).collect(Collectors.toList());
    }

    public PokemonDto getPokemonById(Long pokeId) throws ResourceNotFoundException{
        Optional<PokemonModel> optPoke = repository.findById(pokeId);
        if (optPoke.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find pokemon with id "+pokeId);
        }
        return new PokemonDto().wrap(optPoke.get());
    }

    public void deletePokemonById(Long pokeId) throws ResourceNotFoundException{
        Optional<PokemonModel> optPoke = repository.findById(pokeId);
        if (optPoke.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find pokemon with id "+pokeId);
        }
        repository.delete(optPoke.get());
    }


    public PokemonDto updateById(Long pokeId, PokemonModel pokemon) throws ResourceNotFoundException {
        Optional<PokemonModel> oldPoke = repository.findById(pokeId);
        if (oldPoke.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find pokemon with id "+pokeId);
        }
        pokemon.setPokemonId(pokeId);
        repository.saveAndFlush(pokemon);
        return new PokemonDto().wrap(pokemon);
    }

    public void deleteById(Long pokeId) throws ResourceNotFoundException {
        Optional<PokemonModel> oldPoke = repository.findById(pokeId);
        if (oldPoke.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find pokemon with id "+pokeId);
        }
        repository.deleteById(pokeId);
    }

    public void checkFields(PokemonModel pokemonModel) throws BadRequestBodyException {
        if (pokemonModel.getPrice().doubleValue() <= 0) {
            throw new BadRequestBodyException("The price cannot be less or equal to 0");
        }

        if (!StringUtils.hasText(pokemonModel.getName())) {
            throw new BadRequestBodyException("The pokename cannot be empty");
        }

        if (pokemonModel.getFirstType() == null) {
            throw new BadRequestBodyException("The pokemon need to have at least 1 type");
        }

        if (!StringUtils.hasText(pokemonModel.getCreatedBy())) {
            throw new BadRequestBodyException("The pokemon needs to be created by someone");
        }
    }

}
