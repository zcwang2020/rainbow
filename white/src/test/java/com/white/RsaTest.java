package com.white;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author: tmind
 * @Date: 2024/10/14 12:19
 * @Description:
 */
public class RsaTest {

    @Test
    void rsaTest() {
        try {
            // 创建 KeyPairGenerator 实例
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            // 设置密钥长度为 2048 位
            keyPairGenerator.initialize(2048);

            // 生成公私钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // 获取公钥和私钥
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // 将公钥以默认格式（X.509）输出到文件
            String defaultPublicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            writeToFile(defaultPublicKeyBase64, "d:\\public_key_default.txt");

            // 将公钥转换为 PKCS#8 格式并输出到文件
            byte[] pkcs8PublicKeyBytes = convertToPKCS8(publicKey);
            String pkcs8PublicKeyBase64 = Base64.getEncoder().encodeToString(pkcs8PublicKeyBytes);
            writeToFile(pkcs8PublicKeyBase64, "d:\\public_key_pkcs8.txt");

            // 将私钥输出到文件
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            writeToFile(privateKeyBase64, "d:\\private_key.txt");

            System.out.println("RSA keys have been written to files.");
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            System.err.println("Failed to generate RSA key pair or write to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeToFile(String content, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        }
    }

    private static byte[] convertToPKCS8(PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 将公钥转换为 PKCS#8 格式
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509KeySpec).getEncoded();
    }

}
