package com.evaluacion.apitransacciones.controller;

import com.evaluacion.apitransacciones.dto.CancelacionRequest;
import com.evaluacion.apitransacciones.dto.TransaccionRequest;
import com.evaluacion.apitransacciones.dto.TransaccionResponse;
import com.evaluacion.apitransacciones.entity.Transaccion;
import com.evaluacion.apitransacciones.service.TransaccionService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService service;

    public TransaccionController(TransaccionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransaccionResponse> guardar(
            @RequestBody TransaccionRequest request) {

        return ResponseEntity.ok(
                service.guardar(request)
        );
    }

    @GetMapping
    public ResponseEntity<Page<Transaccion>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int cantidad,
            @RequestParam(defaultValue = "id") String ordenarPor) {

        return ResponseEntity.ok(
                service.listar(
                        pagina,
                        cantidad,
                        ordenarPor
                )
        );
    }

    @PatchMapping
    public ResponseEntity<String> cancelar(
            @RequestBody CancelacionRequest request) {

        return ResponseEntity.ok(
                service.cancelar(request)
        );
    }
}