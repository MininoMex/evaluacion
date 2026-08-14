package com.evaluacion.apitransacciones.repository;

import com.evaluacion.apitransacciones.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);
}