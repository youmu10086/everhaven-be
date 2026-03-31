package com.cyf.everhavenbe.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private static KeyPair keyPair;

    static {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate RSA key pair", e);
        }
    }

    /**
     * Get the public key as a Base64 encoded string.
     */
    public static String getPublicKey() {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    /**
     * Decrypt a Base64 encoded encrypted string using the private key.
     */
    public static String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}

