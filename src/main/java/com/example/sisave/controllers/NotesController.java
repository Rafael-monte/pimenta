package com.example.sisave.controllers;

import com.example.sisave.exceptions.EntriesNotConfiguredException;
import com.example.sisave.exceptions.ServerException;
import com.example.sisave.models.Usuario;
import com.example.sisave.models.exception.SimpleErrorMessageModel;
import com.example.sisave.services.NotesService;
import com.example.sisave.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
            return ResponseEntity.ok().body(this.service.getNotesFromOldSystem(person, noteIndex));
        } catch (IndexOutOfBoundsException ioobe) {
            return ResponseEntity.badRequest().body(
                    new SimpleErrorMessageModel(
                            Constants.DEFAULT_BAD_REQUEST_MESSAGE,
                            String.format("Bad index: [%s]", noteIndex.toString())
                    )
            );
        } catch (EntriesNotConfiguredException ence) {
            return ResponseEntity.badRequest().body(new SimpleErrorMessageModel(
                    Constants.DEFAULT_BAD_REQUEST_MESSAGE,
                    ence.getMessage()
            ));
        } catch(ServerException se) {
            return ResponseEntity.internalServerError().body(
                    new SimpleErrorMessageModel("Occoured an internal exception. Try again later.",
                            "Cant deserialize request token")
            );
        }
    }
}
