package com.example.sisave.services;

import com.example.sisave.exceptions.BadRequestBodyException;
import com.example.sisave.exceptions.ServerException;
import com.example.sisave.exceptions.UserNotFoundException;
import com.example.sisave.handlers.PasswordHandler;
import com.example.sisave.models.Usuario;
import com.example.sisave.models.auth.AuthResponseModel;
import com.example.sisave.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthService authService;

    @Transactional
    public void savePerson(Usuario person) throws BadRequestBodyException, ServerException {
        this.verifyBlankFields(person);
        Optional<Usuario> optUsuario = repository.getUsuarioByEmail(person.getEmail());
        if (optUsuario.isPresent()) {
            throw new BadRequestBodyException(String.format("The email '%s' is already in use.", person.getEmail()));
        }
        person.setSecret(PasswordHandler.encryptSecret(person));
        repository.saveAndFlush(person);
    }

    @Transactional
    public void updatePersonById(Usuario person, Long id) throws BadRequestBodyException {
        this.verifyBlankFields(person);
        Optional<Usuario> optUsuario = repository.getUsuarioByUserId(id);
        if (optUsuario.isEmpty()) {
            throw new BadRequestBodyException(String.format("The email '%s' wasn't found.", person.getEmail()));
        }
        repository.saveAndFlush(person);
    }


    @Transactional
    public AuthResponseModel updateSecret(String newSecret, String oldSecret, Usuario person) throws BadRequestBodyException {
        this.verifySecrets(newSecret, oldSecret);
        String decryptedSecret = PasswordHandler.decryptSecret(person);

        if (!decryptedSecret.equals(oldSecret)) {
            throw new BadRequestBodyException("The provided secret isn't correct.");
        }
        person.setSecret(newSecret);
        person.setSecret(PasswordHandler.encryptSecret(person));
        repository.saveAndFlush(person);
        return new AuthResponseModel(authService.generateNewTokenAfterUpdatePerson(person));
    }

    private void verifyBlankFields(Usuario person) throws BadRequestBodyException{
        if (!StringUtils.hasText(person.getUsername())) {
            throw new BadRequestBodyException(String.format("The username cannot be empty"));
        }

        if (!StringUtils.hasText(person.getEmail())) {
            throw new BadRequestBodyException("The email cannot be empty");
        }

        if (!StringUtils.hasText(person.getSecret())) {
            throw new BadRequestBodyException("The password cannot be empty");
        }
    }

    private void verifySecrets(String newSecret, String oldSecret) {
        if (!StringUtils.hasText(newSecret)) {
            throw new BadRequestBodyException(String.format("The new secret cannot be empty"));
        }

        if (!StringUtils.hasText(oldSecret)) {
            throw new BadRequestBodyException("The old secret cannot be empty");
        }
    }

    public AuthService getAuth() {
        return this.authService;
    }

}
