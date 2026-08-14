package com.evaluacion.apioperaciones.controller;

import com.evaluacion.apioperaciones.exception.GlobalExceptionHandler;
import com.evaluacion.apioperaciones.service.OperacionService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OperacionController.class)
@Import(GlobalExceptionHandler.class)
class OperacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OperacionService operacionService;


    @Test
    void rechazarOperacionConNumeros()
            throws Exception {

        String json = """
                {
                    "operacion": "venta123",
                    "importe": 100.00,
                    "cliente": "Angel",
                    "secreto": "abc123"
                }
                """;


        mockMvc.perform(
                        post("/api/operaciones")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(json)
                )

                .andExpect(
                        status().isBadRequest()
                )

                .andExpect(
                        jsonPath(
                                "$.operacion"
                        ).exists()
                );
    }


    @Test
    void rechazarClienteConNumeros()
            throws Exception {

        String json = """
                {
                    "operacion": "venta",
                    "importe": 100.00,
                    "cliente": "Angel123",
                    "secreto": "abc123"
                }
                """;


        mockMvc.perform(
                        post("/api/operaciones")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(json)
                )

                .andExpect(
                        status().isBadRequest()
                )

                .andExpect(
                        jsonPath(
                                "$.cliente"
                        ).exists()
                );
    }


    @Test
    void rechazarImporteConMasDeDosDecimales()
            throws Exception {

        String json = """
                {
                    "operacion": "venta",
                    "importe": 100.999,
                    "cliente": "Angel",
                    "secreto": "abc123"
                }
                """;


        mockMvc.perform(
                        post("/api/operaciones")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(json)
                )

                .andExpect(
                        status().isBadRequest()
                )

                .andExpect(
                        jsonPath(
                                "$.importe"
                        ).exists()
                );
    }


    @Test
    void rechazarCamposVacios()
            throws Exception {

        String json = """
                {
                    "operacion": "",
                    "importe": 100.00,
                    "cliente": "",
                    "secreto": ""
                }
                """;


        mockMvc.perform(
                        post("/api/operaciones")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(json)
                )

                .andExpect(
                        status().isBadRequest()
                )

                .andExpect(
                        jsonPath(
                                "$.operacion"
                        ).exists()
                )

                .andExpect(
                        jsonPath(
                                "$.cliente"
                        ).exists()
                )

                .andExpect(
                        jsonPath(
                                "$.secreto"
                        ).exists()
                );
    }
}