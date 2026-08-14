package com.evaluacion.apioperaciones.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private boolean correcto;

    private String mensaje;
}