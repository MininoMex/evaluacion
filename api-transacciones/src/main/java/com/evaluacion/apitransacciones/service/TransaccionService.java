package com.evaluacion.apitransacciones.service;

import com.evaluacion.apitransacciones.dto.CancelacionRequest;
import com.evaluacion.apitransacciones.dto.TransaccionRequest;
import com.evaluacion.apitransacciones.dto.TransaccionResponse;
import com.evaluacion.apitransacciones.entity.Transaccion;
import com.evaluacion.apitransacciones.repository.TransaccionRepository;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class TransaccionService {

    private final TransaccionRepository repository;

    public TransaccionService(TransaccionRepository repository) {
        this.repository = repository;
    }

    public TransaccionResponse guardar(TransaccionRequest request) {

        Transaccion transaccion = new Transaccion();

        transaccion.setOperacion(request.getOperacion());
        transaccion.setImporte(request.getImporte());
        transaccion.setCliente(request.getCliente());

        transaccion.setReferencia(generarReferencia());

        transaccion.setEstatus("Aprobada");

        // Requisito de la evaluación
        transaccion.setSecreto("secreto");

        Transaccion guardada = repository.save(transaccion);

        return new TransaccionResponse(
                guardada.getId(),
                guardada.getEstatus(),
                guardada.getReferencia(),
                guardada.getOperacion()
        );
    }

    public Page<Transaccion> listar(
            int pagina,
            int cantidad,
            String ordenarPor) {

        Pageable pageable = PageRequest.of(
                pagina,
                cantidad,
                Sort.by(ordenarPor)
        );

        return repository.findAll(pageable);
    }

    @Transactional
    public String cancelar(CancelacionRequest request) {

        if (!"cancelar".equalsIgnoreCase(request.getEstatus())) {
            throw new IllegalArgumentException(
                    "El estatus debe contener el valor cancelar"
            );
        }

        int registros = repository.cancelarTransaccion(
                request.getId(),
                request.getReferencia()
        );

        if (registros == 0) {
            throw new IllegalArgumentException(
                    "No se encontró una transacción aprobada con esos datos"
            );
        }

        return "Transacción cancelada correctamente";
    }

    private String generarReferencia() {

        int numero = ThreadLocalRandom
                .current()
                .nextInt(100000, 1000000);

        return String.valueOf(numero);
    }
}