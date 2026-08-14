package com.evaluacion.apitransacciones.service;

import com.evaluacion.apitransacciones.dto.LoginRequest;
import com.evaluacion.apitransacciones.dto.LoginResponse;
import com.evaluacion.apitransacciones.entity.Usuario;
import com.evaluacion.apitransacciones.repository.UsuarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(
            UsuarioRepository repository,
            BCryptPasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = repository
                .findByUsuario(request.getUsuario())
                .orElse(null);

        if (usuario == null) {
            return new LoginResponse(
                    false,
                    "Usuario incorrecto"
            );
        }

        boolean correcto = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword()
        );

        if (!correcto) {
            return new LoginResponse(
                    false,
                    "Password incorrecto"
            );
        }

        return new LoginResponse(
                true,
                "Login correcto"
        );
    }
}