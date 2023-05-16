package com.example.sisave.controllers;

import com.example.sisave.exceptions.ServerException;
import com.example.sisave.models.Usuario;
import com.example.sisave.exceptions.BadRequestBodyException;
import com.example.sisave.models.exception.SimpleErrorMessageModel;
import com.example.sisave.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private UsuarioService service;

    @GetMapping(path="/say-hi/{name}")
    public ResponseEntity<?> sayHi(@PathVariable(name="name") String name) {
        return ResponseEntity.ok().body(String.format("Hi %s", name));
    }


    @PostMapping(path="/save", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePerson(@RequestBody Usuario person) {
        try {
            service.savePerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestBodyException bde) {
            return ResponseEntity.badRequest().body(
                    new SimpleErrorMessageModel(
                            "The request body is invalid",
                            bde.getMessage()
                    )
            );
        } catch (ServerException se) {
            return ResponseEntity.internalServerError().body(new SimpleErrorMessageModel(se.getMessage(), null));
        }
    }

}
