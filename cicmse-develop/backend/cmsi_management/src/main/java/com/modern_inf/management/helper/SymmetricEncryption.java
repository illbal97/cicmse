package com.modern_inf.management.helper;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class SymmetricEncryption {

    @Value("${symmetric.key}")
    private String SYMMETRIC_KEY ;

    public String encrypt(String plainText) throws Exception{
        SecretKey secretKey = new SecretKeySpec(SYMMETRIC_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encrypted) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
        SecretKey secretKey = new SecretKeySpec(SYMMETRIC_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, "UTF-8");
    }

}
