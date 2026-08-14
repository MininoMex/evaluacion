package com.evaluacion.apitransacciones.service;

import com.evaluacion.apitransacciones.dto.LoginRequest;
import com.evaluacion.apitransacciones.dto.LoginResponse;
import com.evaluacion.apitransacciones.entity.Usuario;
import com.evaluacion.apitransacciones.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UsuarioRepository repository;

    private BCryptPasswordEncoder passwordEncoder;

    private LoginService service;


    @BeforeEach
    void setUp() {

        passwordEncoder =
                new BCryptPasswordEncoder();

        service =
                new LoginService(
                        repository,
                        passwordEncoder
                );
    }


    @Test
    void loginCorrecto() {

        // Arrange
        LoginRequest request =
                new LoginRequest();

        request.setUsuario("admin");
        request.setPassword("123456");


        Usuario usuario =
                new Usuario();

        usuario.setId(1L);
        usuario.setUsuario("admin");

        usuario.setPassword(
                passwordEncoder.encode(
                        "123456"
                )
        );


        when(
                repository.findByUsuario("admin")
        ).thenReturn(
                Optional.of(usuario)
        );


        // Act
        LoginResponse response =
                service.login(request);


        // Assert
        assertNotNull(response);

        assertTrue(
                response.isCorrecto()
        );

        assertEquals(
                "Login correcto",
                response.getMensaje()
        );


        verify(repository)
                .findByUsuario("admin");
    }


    @Test
    void passwordIncorrecto() {

        // Arrange
        LoginRequest request =
                new LoginRequest();

        request.setUsuario("admin");

        request.setPassword(
                "passwordMalo"
        );


        Usuario usuario =
                new Usuario();

        usuario.setId(1L);
        usuario.setUsuario("admin");

        usuario.setPassword(
                passwordEncoder.encode(
                        "123456"
                )
        );


        when(
                repository.findByUsuario("admin")
        ).thenReturn(
                Optional.of(usuario)
        );


        // Act
        LoginResponse response =
                service.login(request);


        // Assert
        assertNotNull(response);

        assertFalse(
                response.isCorrecto()
        );

        assertEquals(
                "Password incorrecto",
                response.getMensaje()
        );
    }


    @Test
    void usuarioNoExiste() {

        // Arrange
        LoginRequest request =
                new LoginRequest();

        request.setUsuario("noExiste");
        request.setPassword("123456");


        when(
                repository.findByUsuario(
                        "noExiste"
                )
        ).thenReturn(
                Optional.empty()
        );


        // Act
        LoginResponse response =
                service.login(request);


        // Assert
        assertNotNull(response);

        assertFalse(
                response.isCorrecto()
        );

        assertEquals(
                "Usuario incorrecto",
                response.getMensaje()
        );


        verify(repository)
                .findByUsuario(
                        "noExiste"
                );
    }
}