package com.evaluacion.apioperaciones.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OperacionRequest {

    @NotBlank(message = "La operación es obligatoria")
    @Size(
            min = 2,
            max = 30,
            message = "La operación debe tener entre 2 y 30 caracteres"
    )
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$",
            message = "La operación solo puede contener letras"
    )
    private String operacion;


    @NotNull(message = "El importe es obligatorio")
    @DecimalMin(
            value = "0.01",
            message = "El importe debe ser mayor a cero"
    )
    @Digits(
            integer = 10,
            fraction = 2,
            message = "El importe debe tener máximo 2 decimales"
    )
    private BigDecimal importe;


    @NotBlank(message = "El cliente es obligatorio")
    @Size(
            min = 2,
            max = 50,
            message = "El cliente debe tener entre 2 y 50 caracteres"
    )
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$",
            message = "El cliente solo puede contener letras"
    )
    private String cliente;


    @NotBlank(message = "El secreto es obligatorio")
    private String secreto;
}