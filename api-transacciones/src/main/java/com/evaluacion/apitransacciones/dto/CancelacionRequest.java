package com.evaluacion.apitransacciones.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelacionRequest {

    private Long id;
    private String referencia;
    private String estatus;
}