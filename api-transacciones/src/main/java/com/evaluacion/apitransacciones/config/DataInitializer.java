package com.evaluacion.apitransacciones.config;

import com.evaluacion.apitransacciones.entity.Usuario;
import com.evaluacion.apitransacciones.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner iniciarUsuario(
            UsuarioRepository repository,
            BCryptPasswordEncoder encoder) {

        return args -> {

            if (repository.findByUsuario("admin").isEmpty()) {

                Usuario usuario = new Usuario();

                usuario.setUsuario("admin");

                usuario.setPassword(
                        encoder.encode("123456")
                );

                repository.save(usuario);
            }
        };
    }
}