package com.evaluacion.apitransacciones.controller;

import com.evaluacion.apitransacciones.dto.LoginRequest;
import com.evaluacion.apitransacciones.dto.LoginResponse;
import com.evaluacion.apitransacciones.service.LoginService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                loginService.login(request)
        );
    }
}