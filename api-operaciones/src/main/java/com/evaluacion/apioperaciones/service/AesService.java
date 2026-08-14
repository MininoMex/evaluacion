package com.evaluacion.apioperaciones.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AesService {

    @Value("${aes.secret}")
    private String secret;

    public String descifrar(String textoCifrado) {

        try {

            // El frontend manda IV + información cifrada
            byte[] datos =
                    Base64.getDecoder()
                            .decode(textoCifrado);


            // Los primeros 12 bytes corresponden al IV
            byte[] iv =
                    Arrays.copyOfRange(
                            datos,
                            0,
                            12
                    );


            // El resto corresponde al texto cifrado
            byte[] cifrado =
                    Arrays.copyOfRange(
                            datos,
                            12,
                            datos.length
                    );


            // Generamos una clave de 256 bits
            MessageDigest digest =
                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            byte[] key =
                    digest.digest(
                            secret.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );


            SecretKeySpec secretKey =
                    new SecretKeySpec(
                            key,
                            "AES"
                    );


            Cipher cipher =
                    Cipher.getInstance(
                            "AES/GCM/NoPadding"
                    );


            GCMParameterSpec spec =
                    new GCMParameterSpec(
                            128,
                            iv
                    );


            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    spec
            );


            byte[] resultado =
                    cipher.doFinal(
                            cifrado
                    );


            return new String(
                    resultado,
                    StandardCharsets.UTF_8
            );

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "No fue posible descifrar el secreto"
            );
        }
    }
}