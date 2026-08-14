package com.evaluacion.apioperaciones.service;

import com.evaluacion.apioperaciones.dto.OperacionInternaRequest;
import com.evaluacion.apioperaciones.dto.OperacionRequest;
import com.evaluacion.apioperaciones.dto.OperacionResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OperacionService {

    private final RestTemplate restTemplate;

    private final AesService aesService;


    @Value("${api.transacciones.url}")
    private String apiTransaccionesUrl;


    public OperacionService(
            RestTemplate restTemplate,
            AesService aesService) {

        this.restTemplate = restTemplate;
        this.aesService = aesService;
    }


    public OperacionResponse procesar(
            OperacionRequest request) {

        // 1. Desciframos el secreto que llegó del frontend
        String secretoDescifrado =
                aesService.descifrar(
                        request.getSecreto()
                );


        // 2. Creamos el objeto que enviaremos a API 2
        OperacionInternaRequest interna =
                new OperacionInternaRequest(
                        request.getOperacion(),
                        request.getImporte(),
                        request.getCliente(),
                        secretoDescifrado
                );


        // 3. Enviamos los datos a API 2
        return restTemplate.postForObject(

                apiTransaccionesUrl
                        + "/api/transacciones",

                interna,

                OperacionResponse.class
        );
    }
}