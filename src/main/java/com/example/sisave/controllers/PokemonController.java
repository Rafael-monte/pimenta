package com.example.sisave.controllers;

import com.example.sisave.exceptions.BadRequestBodyException;
import com.example.sisave.exceptions.ServerException;
import com.example.sisave.exceptions.UserNotFoundException;
import com.example.sisave.models.PokemonModel;
import com.example.sisave.models.UsuarioModel;
import com.example.sisave.models.dto.PokemonDto;
import com.example.sisave.models.exception.SimpleErrorMessageModel;
import com.example.sisave.services.AuthService;
import com.example.sisave.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PokemonService service;

    @GetMapping
    @CrossOrigin("*")
    public ResponseEntity<?> listAllPokemon(@RequestHeader("u-token") String token) {
        try {
            UsuarioModel model = this.authService.getUserFromAuth(token);
            return ResponseEntity.ok(this.service.getAllPokemonsNotCreatedByCurrentUser(model.getEmail()));
        } catch (ServerException | UserNotFoundException se) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleErrorMessageModel(
                    "Error when try to deserialize token",
                    se.getMessage()
            ));
        }
    }


    @GetMapping("/user")
    @CrossOrigin("*")
    public ResponseEntity<?> listUsersPokemons(@RequestHeader("u-token") String token) {
        try {
            UsuarioModel model = this.authService.getUserFromAuth(token);
            return ResponseEntity.ok(this.service.getAllPokemonsCreatedByCurrentUser(model.getEmail()));
        } catch (ServerException | UserNotFoundException se) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleErrorMessageModel(
                    "Error when try to deserialize token",
                    se.getMessage()
            ));
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<?> getPokemonById(@PathVariable("id") Long pokeId, @RequestHeader("u-token") String token) {
        try {
            this.authService.getUserFromAuth(token);
            return ResponseEntity.ok(this.service.getPokemonById(pokeId));
        } catch (ServerException | UserNotFoundException se) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleErrorMessageModel(
                    "Error when try to deserialize token",
                    se.getMessage()
            ));
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @CrossOrigin("*")
    public ResponseEntity<?> savePokemon(@RequestBody PokemonDto pokemonDto, @RequestHeader("u-token") String token) {
        try {
            UsuarioModel model = this.authService.getUserFromAuth(token);
            pokemonDto.setCreatedBy(model.getEmail());
            this.service.save(pokemonDto.unwrap());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ServerException | UserNotFoundException se) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleErrorMessageModel(
                    "Error when try to deserialize token",
                    se.getMessage()
            ));
        } catch (BadRequestBodyException bre) {
            return ResponseEntity.badRequest().body(
                    new SimpleErrorMessageModel(
                    "Could not save pokemon",
                    bre.getMessage()
                )
            );
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<?> updatePokemon(@RequestBody PokemonDto pokemon, @PathVariable("id") Long pokemonId,
                                           @RequestHeader("u-token") String token) {
        try {
            UsuarioModel model = this.authService.getUserFromAuth(token);
            pokemon.setCreatedBy(model.getEmail());
            this.service.updateById(pokemonId, pokemon.unwrap());
            return ResponseEntity.noContent().build();
        } catch (ServerException | UserNotFoundException se) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleErrorMessageModel(
                    "Error when try to deserialize token",
                    se.getMessage()
            ));
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.notFound().build();
        }

    }


    @DeleteMapping("/{id}")
    @CrossOrigin("*")
    public ResponseEntity<?> deletePokemonById(@PathVariable("id") Long id, @RequestHeader("u-token") String token) {
        try {
            this.authService.getUserFromAuth(token);
            this.service.deletePokemonById(id);
            return ResponseEntity.noContent().build();
        } catch (ServerException | UserNotFoundException se) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleErrorMessageModel(
                    "Error when try to deserialize token",
                    se.getMessage()
            ));
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.notFound().build();
        }
    }
}
