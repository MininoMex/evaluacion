package com.evaluacion.apitransacciones.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransaccionRequest {

    private String operacion;
    private BigDecimal importe;
    private String cliente;
    private String secreto;
}