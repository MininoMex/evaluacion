package com.evaluacion.apioperaciones.controller;

import com.evaluacion.apioperaciones.dto.LoginRequest;
import com.evaluacion.apioperaciones.dto.LoginResponse;
import com.evaluacion.apioperaciones.service.LoginService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    private final LoginService service;


    public LoginController(
            LoginService service) {

        this.service = service;
    }


    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {


        return ResponseEntity.ok(
                service.login(request)
        );
    }
}