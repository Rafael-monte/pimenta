package com.example.sisave.controllers;

import com.example.sisave.models.auth.AuthEntriesModel;
import com.example.sisave.models.auth.AuthResponseModel;
import com.example.sisave.models.exception.SimpleErrorMessageModel;
import com.example.sisave.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path="/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path="/token", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@ModelAttribute AuthEntriesModel entries) {
        try {
            String token = authService.generateToken(entries);
            return ResponseEntity.ok().body(new AuthResponseModel(token));
        } catch(NoSuchAlgorithmException nsae) {
            return ResponseEntity.badRequest().body(new SimpleErrorMessageModel(
                    "An error occoured when try to generate the token. Try again later.",
                    null
            ));
        }
    }

}
