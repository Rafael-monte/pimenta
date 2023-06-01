package com.example.sisave.services;
import com.example.sisave.exceptions.BadRequestBodyException;
import com.example.sisave.exceptions.ServerException;
import com.example.sisave.handlers.PasswordHandler;
import com.example.sisave.models.Usuario;
import com.example.sisave.models.auth.AuthEntriesModel;
import com.example.sisave.exceptions.UserNotFoundException;
import com.example.sisave.repositories.UsuarioRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AuthService {

    @Autowired
    private UsuarioRepository userRepository;

    @Value("${auth.encryption.secret-key}")
    private String secretKey;

    private final String SEPARATOR=":";

    public String generateToken(AuthEntriesModel entries) throws ServerException, BadRequestBodyException {
        checkIfUserExists(entries);
        String input = String.format("%s:%s", entries.getEmail(), entries.getPassword());
        return encrypt(input);
    }

    public String generateNewTokenAfterUpdatePerson(Usuario person) throws ServerException, BadRequestBodyException {
        String input = String.format("%s:%s", person.getEmail(), person.getSecret());
        return encrypt(input);
    }


    private void checkIfUserExists(AuthEntriesModel entriesModel) throws BadRequestBodyException {
        Optional<Usuario> optUser = userRepository.getUsuarioByEmail(entriesModel.getEmail());
        if (optUser.isEmpty()) {
            throw new BadRequestBodyException("Cannot find the user by given credentials");
        }

        String decryptedPassword = PasswordHandler.decryptSecret(optUser.get());
        if (!decryptedPassword.equals(entriesModel.getPassword())) {
            throw new BadRequestBodyException("The given credentials doesn't match");
        }
    }

    private String encrypt(String input) throws ServerException {
        byte[] encrypted = applyCipher(Cipher.ENCRYPT_MODE, input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }


    private String decrypt(String input) throws ServerException {
        try {
            byte[] withoutBase64 = Base64.getDecoder().decode(input);
            byte[] decrypted = this.applyCipher(Cipher.DECRYPT_MODE, withoutBase64);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch(Exception e) {
            throw new ServerException("Occoured an error while try to decode token");
        }
    }

    public Usuario getUserFromAuth(String tokenHeader) throws ServerException {
        String decryptedString = this.decrypt(tokenHeader);
        List<String> credentials = List.of(decryptedString.split(this.SEPARATOR));
        Optional<Usuario> optUser = userRepository.getUsuarioByEmail(credentials.stream().findFirst()
                .orElseThrow(()-> new ServerException("Cannot deserialize token")));
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("Cannot find user by using its token");
        }
        String userSecret = PasswordHandler.decryptSecret(optUser.get());
        if (!userSecret.equals(credentials.get(1))) {
            throw new UserNotFoundException("Cannot find user by using its token");
        }
        return optUser.get();
    }


    public void addSensibleInformationToUser(Usuario person, String token) {
        Usuario currentUser = this.getUserFromAuth(token);
        person.setSecret(currentUser.getSecret());
        person.setUserId(currentUser.getUserId());
    }


    private byte[] applyCipher(final int CIPHER_MODE, byte[] input) throws ServerException {
       try {
           SecretKeySpec key = new SecretKeySpec(this.secretKey.getBytes(StandardCharsets.UTF_8), "AES");
           Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
           cipher.init(CIPHER_MODE, key);
           return cipher.doFinal(input);
       } catch (Exception e) {
           log.log(Level.ERROR, String.format("Ocorreu um erro ao gerar token: %s", e.getCause()));
           throw new ServerException("Cannot generate token");
       }
    }
}
