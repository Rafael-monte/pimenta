package com.example.sisave.services;
import com.example.sisave.models.auth.AuthEntriesModel;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@Log4j2
public class AuthService {

    @Value("${auth.encryption.secret-key}")
    private String secretKey;

    @Value("${auth.encryption.cipher-algorithm}")
    private String ALGORITHM_ENCRIPTION_TYPE;

    @Value("${auth.encryption.cipher-padding}")
    private String CYPHER_PADDING;

    public String generateToken(AuthEntriesModel entries) throws NoSuchAlgorithmException {
        String input = String.format("%s:%s", entries.getUsername(), entries.getPassword());
        return encrypt(input);
    }

    private String encrypt(String input) throws NoSuchAlgorithmException {
        byte[] encrypted = applyCipher(Cipher.ENCRYPT_MODE, input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }


    private String decrypt(String input) throws NoSuchAlgorithmException {
        byte[] withoutBase64 = Base64.getDecoder().decode(input);
        byte[] decrypted = this.applyCipher(Cipher.DECRYPT_MODE, withoutBase64);
        return new String(decrypted, StandardCharsets.UTF_8);
    }


    private byte[] applyCipher(final int CIPHER_MODE, byte[] input) throws NoSuchAlgorithmException {
       try {
           SecretKeySpec key = new SecretKeySpec(this.secretKey.getBytes(StandardCharsets.UTF_8), this.ALGORITHM_ENCRIPTION_TYPE);
           Cipher cipher = Cipher.getInstance(this.CYPHER_PADDING);
           cipher.init(CIPHER_MODE, key);
           return cipher.doFinal(input);
       } catch (Exception e) {
           log.log(Level.ERROR, String.format("Ocorreu um erro ao gerar token: %s", e.getCause()));
           throw new NoSuchAlgorithmException();
       }
    }
}
