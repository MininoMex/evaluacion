package com.evaluacion.apioperaciones.controller;

import com.evaluacion.apioperaciones.dto.OperacionRequest;
import com.evaluacion.apioperaciones.dto.OperacionResponse;
import com.evaluacion.apioperaciones.service.OperacionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operaciones")
public class OperacionController {

    private final OperacionService service;


    public OperacionController(
            OperacionService service) {

        this.service = service;
    }


    @PostMapping
    public ResponseEntity<OperacionResponse> procesar(

            @Valid
            @RequestBody OperacionRequest request) {

        OperacionResponse response =
                service.procesar(request);


        return ResponseEntity.ok(
                response
        );
    }
}