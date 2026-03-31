package com.cyf.everhavenbe.utils;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    // RSA 加密算法，密钥长度，密钥文件路径等常量
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private static final String KEY_DIR = "keys";
    private static final String PUBLIC_KEY_FILE = KEY_DIR + File.separator + "publicKey.dat";
    private static final String PRIVATE_KEY_FILE = KEY_DIR + File.separator + "privateKey.dat";

    private static final KeyPair keyPair;

    static {
        try {
            keyPair = initKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RSA keys", e);
        }
    }

    private static KeyPair initKeyPair() throws Exception {
        File dir = new File(KEY_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File pubFile = new File(PUBLIC_KEY_FILE);
        File priFile = new File(PRIVATE_KEY_FILE);

        if (pubFile.exists() && priFile.exists()) {
            // Load keys from file
            byte[] pubBytes = Files.readAllBytes(pubFile.toPath());
            byte[] priBytes = Files.readAllBytes(priFile.toPath());

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubBytes);
            PublicKey publicKey = keyFactory.generatePublic(pubSpec);

            PKCS8EncodedKeySpec priSpec = new PKCS8EncodedKeySpec(priBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(priSpec);

            return new KeyPair(publicKey, privateKey);
        } else {
            // Generate new keys
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair kp = keyPairGenerator.generateKeyPair();

            // Save keys to file
            Files.write(pubFile.toPath(), kp.getPublic().getEncoded());
            Files.write(priFile.toPath(), kp.getPrivate().getEncoded());

            return kp;
        }
    }

    /**
     * Get the public key as a Base64 encoded string.
     */
    public static String getPublicKey() {
        //  keyPair.getPublic().getEncoded()= “把公钥按标准格式转成字节数组，方便你做 Base64/PEM/传输/存储等操作”
        //  把二进制数据转成 Base64 字符串，方便你在文本环境中使用（比如在 JSON、XML、HTTP 头部等地方传输公钥）
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

