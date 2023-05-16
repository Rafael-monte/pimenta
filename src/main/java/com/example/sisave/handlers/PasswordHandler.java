package com.example.sisave.handlers;

import com.example.sisave.exceptions.ServerException;
import com.example.sisave.models.Usuario;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
@Log4j2
public class PasswordHandler {
    private static final List<Integer> AVAILABLE_BYTE_LENGTHS = Arrays.asList(16, 24, 32);

    public static String encryptSecret(Usuario person) throws ServerException {
        try {
            SecretKeySpec key = new SecretKeySpec(generateKey(person).getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(person.getSecret().getBytes(StandardCharsets.UTF_8)));
            return encrypted;
        } catch (Exception e) {
            throw new ServerException("An error occoured when encrypt the password: "+e.getCause());
        }
    }


    public static String decryptSecret(Usuario person) throws ServerException {
        try {
            SecretKeySpec key = new SecretKeySpec(generateKey(person).getBytes(StandardCharsets.UTF_8), "AES");
            byte[] cipherEncryptedPassoword = Base64.getDecoder().decode(person.getSecret().getBytes(StandardCharsets.UTF_8));
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(cipherEncryptedPassoword));
        }   catch (Exception e) {
            throw new ServerException("An error occoured when decrypt the password: "+e.getCause());
        }
    }


    private static String generateKey(Usuario person) {
        String idAsString = person.getUserId().toString();
        String key = "";
        for (final Integer BYTE_LENGTH: AVAILABLE_BYTE_LENGTHS) {
            if (BYTE_LENGTH >= idAsString.length()) {
                int keyLength = BYTE_LENGTH - idAsString.length();
                key = addZerosToKey(key, keyLength);
                key+=idAsString;
                break;
            }
        }
        return key;
    }


    private static String addZerosToKey(String key, int numberOfZeros) {
        for (int i = 0; i < numberOfZeros; ++i) {
            key+="0";
        }
        return key;
    }
}
