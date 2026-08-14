package com.evaluacion.apioperaciones.service;

import com.evaluacion.apioperaciones.dto.LoginRequest;
import com.evaluacion.apioperaciones.dto.LoginResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {


    private final RestTemplate restTemplate;


    @Value("${api.transacciones.url}")
    private String apiTransaccionesUrl;


    public LoginService(
            RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }


    public LoginResponse login(
            LoginRequest request) {


        return restTemplate.postForObject(

                apiTransaccionesUrl
                        + "/api/auth/login",

                request,

                LoginResponse.class
        );
    }
}