package com.evaluacion.apioperaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OperacionInternaRequest {

    private String operacion;

    private BigDecimal importe;

    private String cliente;

    private String secreto;
}