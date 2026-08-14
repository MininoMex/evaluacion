package com.evaluacion.apitransacciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransaccionResponse {

    private Long id;
    private String estatus;
    private String referencia;
    private String operacion;
}