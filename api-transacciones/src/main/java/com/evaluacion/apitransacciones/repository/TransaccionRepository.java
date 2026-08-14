package com.evaluacion.apitransacciones.repository;

import com.evaluacion.apitransacciones.entity.Transaccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransaccionRepository
        extends JpaRepository<Transaccion, Long> {

    @Modifying
    @Query("""
            UPDATE Transaccion t
            SET t.estatus = 'Cancelada'
            WHERE t.id = :id
            AND t.referencia = :referencia
            AND t.estatus = 'Aprobada'
            """)
    int cancelarTransaccion(
            @Param("id") Long id,
            @Param("referencia") String referencia
    );
}