package com.example.sisave.controllers;

import com.example.sisave.exceptions.CommunicationFailException;
import com.example.sisave.exceptions.EntriesNotConfiguredException;
import com.example.sisave.exceptions.ServerException;
import com.example.sisave.models.Usuario;
import com.example.sisave.models.exception.SimpleErrorMessageModel;
import com.example.sisave.services.NotesService;
import com.example.sisave.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService service;

    @GetMapping("/{noteIndex}")
    public ResponseEntity<?> getNotesFromUser(
            @PathVariable("noteIndex") Integer noteIndex,
            @RequestHeader("u-token") String token) {
        try {
            Usuario person = this.service.getAuth().getUserFromAuth(token);
            return ResponseEntity.ok().body(this.service.getNotes(person, noteIndex));
        } catch (IndexOutOfBoundsException | EntriesNotConfiguredException e) {
            return ResponseEntity.badRequest().body(
                    new SimpleErrorMessageModel(
                            Constants.DEFAULT_BAD_REQUEST_MESSAGE,
                            e.getMessage()
                    )
            );
        } catch(ServerException se) {
            return ResponseEntity.internalServerError().body(
                    new SimpleErrorMessageModel("Occoured an internal exception. Try again later.",
                            "Cant deserialize request token")
            );
        } catch(CommunicationFailException cfe) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                    new SimpleErrorMessageModel("Occoured an error when try to connect with Sisav", cfe.getMessage())
            );
        } catch (JsonProcessingException jpe) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                    new SimpleErrorMessageModel("Cannot deserialize response from middleware", jpe.getMessage()));
        }
    }
}
