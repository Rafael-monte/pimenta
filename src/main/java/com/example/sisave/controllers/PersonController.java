package com.example.sisave.controllers;

import com.example.sisave.exceptions.BadRequestBodyException;
import com.example.sisave.exceptions.ServerException;
import com.example.sisave.models.Usuario;
import com.example.sisave.models.dto.UsuarioDto;
import com.example.sisave.models.exception.SimpleErrorMessageModel;
import com.example.sisave.services.AuthService;
import com.example.sisave.services.UsuarioService;
import com.example.sisave.utils.Constants;
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

    @PostMapping(path="/save", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePerson(@RequestBody UsuarioDto person) {
        try {
            this.service.savePerson(person.unwrap());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestBodyException bde) {
            return ResponseEntity.badRequest().body(
                    new SimpleErrorMessageModel(
                            Constants.DEFAULT_BAD_REQUEST_MESSAGE,
                            bde.getMessage()
                    )
            );
        } catch (ServerException se) {
            return ResponseEntity.internalServerError().body(new SimpleErrorMessageModel(se.getMessage(), null));
        }
    }


    @PutMapping(path="/save", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePerson(@RequestBody UsuarioDto person, @RequestHeader("u-token") String token) {
        try {
            Usuario unwraped = person.unwrap();
            this.service.getAuth().addSensibleInformationToUser(unwraped, token);
            this.service.updatePersonById(unwraped, unwraped.getUserId());
            return ResponseEntity.noContent().build();
        } catch (BadRequestBodyException bde) {
            return ResponseEntity.badRequest().body(
                    new SimpleErrorMessageModel(
                            Constants.DEFAULT_BAD_REQUEST_MESSAGE,
                            bde.getMessage()
                    )
            );
        }
    }


    @PutMapping(path="/update-secret", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> updatePassword(@RequestParam("new-secret") String newSecret,
                                            @RequestParam("old-secret") String oldSecret,
                                            @RequestHeader("u-token") String token) {
        try {
            return ResponseEntity.ok().body(
                    this.service.updateSecret(newSecret, oldSecret, this.service.getAuth().getUserFromAuth(token))
            );
        } catch (BadRequestBodyException be) {
            return ResponseEntity.badRequest().body(
                    new SimpleErrorMessageModel(
                            Constants.DEFAULT_BAD_REQUEST_MESSAGE,
                            be.getMessage()
                    )
            );
        }
    }

}
