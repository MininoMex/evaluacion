package com.evaluacion.apioperaciones.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AesServiceTest {

    private AesService aesService;

    private static final String SECRET =
            "EvaluacionPlataformas2026";


    @BeforeEach
    void setUp() {

        aesService = new AesService();

        // Como en producción el valor viene de application.properties,
        // en el test lo colocamos manualmente.
        ReflectionTestUtils.setField(
                aesService,
                "secret",
                SECRET
        );
    }


    @Test
    void descifrarSecretoCorrectamente() throws Exception {

        // Arrange
        String textoOriginal = "pikachu";

        String textoCifrado =
                cifrar(textoOriginal);


        // Act
        String resultado =
                aesService.descifrar(
                        textoCifrado
                );


        // Assert
        assertEquals(
                textoOriginal,
                resultado
        );
    }


    @Test
    void rechazarTextoCifradoInvalido() {

        // Arrange
        String textoInvalido =
                "esto-no-es-un-aes-valido";


        // Act + Assert
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                aesService.descifrar(
                                        textoInvalido
                                )
                );


        assertEquals(
                "No fue posible descifrar el secreto",
                exception.getMessage()
        );
    }


    /*
     * Este método simula lo que hace React.
     *
     * 1. Genera la misma clave SHA-256.
     * 2. Genera un IV aleatorio.
     * 3. Cifra con AES-GCM.
     * 4. Une IV + contenido cifrado.
     * 5. Convierte todo a Base64.
     */
    private String cifrar(
            String texto) throws Exception {


        // Generamos la clave AES de 256 bits
        MessageDigest digest =
                MessageDigest.getInstance(
                        "SHA-256"
                );

        byte[] key =
                digest.digest(
                        SECRET.getBytes(
                                StandardCharsets.UTF_8
                        )
                );


        SecretKeySpec secretKey =
                new SecretKeySpec(
                        key,
                        "AES"
                );


        // IV de 12 bytes
        byte[] iv =
                new byte[12];

        new SecureRandom()
                .nextBytes(iv);


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
                Cipher.ENCRYPT_MODE,
                secretKey,
                spec
        );


        byte[] cifrado =
                cipher.doFinal(
                        texto.getBytes(
                                StandardCharsets.UTF_8
                        )
                );


        // Unimos IV + información cifrada
        byte[] combinado =
                new byte[
                        iv.length
                                + cifrado.length
                        ];


        System.arraycopy(
                iv,
                0,
                combinado,
                0,
                iv.length
        );


        System.arraycopy(
                cifrado,
                0,
                combinado,
                iv.length,
                cifrado.length
        );


        return Base64
                .getEncoder()
                .encodeToString(
                        combinado
                );
    }
}