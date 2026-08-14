package com.evaluacion.apitransacciones.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "transacciones")
@Getter
@Setter
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operacion;

    private BigDecimal importe;

    private String cliente;

    private String referencia;

    private String estatus;

    private String secreto;
}