package com.modern_inf.management.helper;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
public class SymmetricEncryption {

    @Value("${symmetric.key}")
    private String SYMMETRIC_KEY;

    public String encrypt(String plainText) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(SYMMETRIC_KEY);
        SecretKey secretKey = new SecretKeySpec(Arrays.copyOf(encryptedBytes, 16), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText  = cipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(String encrypted) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(SYMMETRIC_KEY);
        SecretKey secretKey = new SecretKeySpec(Arrays.copyOf(encryptedBytes, 16), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes()));

        return new String(decryptedBytes);
    }


    private String getSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);

        SecretKey secretKey = keyGenerator.generateKey();

        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

}
