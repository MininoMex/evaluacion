package com.evaluacion.apioperaciones.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperacionResponse {

    private Long id;

    private String estatus;

    private String referencia;

    private String operacion;
}