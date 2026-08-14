package com.evaluacion.apitransacciones.service;

import com.evaluacion.apitransacciones.dto.CancelacionRequest;
import com.evaluacion.apitransacciones.dto.TransaccionRequest;
import com.evaluacion.apitransacciones.dto.TransaccionResponse;
import com.evaluacion.apitransacciones.entity.Transaccion;
import com.evaluacion.apitransacciones.repository.TransaccionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionServiceTest {

    @Mock
    private TransaccionRepository repository;

    @InjectMocks
    private TransaccionService service;


    @Test
    void guardarTransaccionCorrectamente() {

        // Arrange
        TransaccionRequest request = new TransaccionRequest();

        request.setOperacion("venta");
        request.setImporte(new BigDecimal("100.00"));
        request.setCliente("Angel");
        request.setSecreto("textoDescifrado");


        when(repository.save(any(Transaccion.class)))
                .thenAnswer(invocacion -> {

                    Transaccion transaccion =
                            invocacion.getArgument(0);

                    transaccion.setId(1L);

                    return transaccion;
                });


        // Act
        TransaccionResponse response =
                service.guardar(request);


        // Assert
        assertNotNull(response);

        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                "Aprobada",
                response.getEstatus()
        );

        assertEquals(
                "venta",
                response.getOperacion()
        );

        assertNotNull(
                response.getReferencia()
        );

        assertTrue(
                response.getReferencia()
                        .matches("\\d{6}")
        );


        // Verificamos qué se mandó realmente al Repository

        ArgumentCaptor<Transaccion> captor =
                ArgumentCaptor.forClass(
                        Transaccion.class
                );

        verify(repository)
                .save(captor.capture());


        Transaccion guardada =
                captor.getValue();


        assertEquals(
                "venta",
                guardada.getOperacion()
        );

        assertEquals(
                new BigDecimal("100.00"),
                guardada.getImporte()
        );

        assertEquals(
                "Angel",
                guardada.getCliente()
        );

        assertEquals(
                "Aprobada",
                guardada.getEstatus()
        );

        assertEquals(
                "secreto",
                guardada.getSecreto()
        );

        assertTrue(
                guardada.getReferencia()
                        .matches("\\d{6}")
        );
    }


    @Test
    void cancelarTransaccionCorrectamente() {

        // Arrange
        CancelacionRequest request =
                new CancelacionRequest();

        request.setId(1L);
        request.setReferencia("123456");
        request.setEstatus("cancelar");


        when(
                repository.cancelarTransaccion(
                        1L,
                        "123456"
                )
        ).thenReturn(1);


        // Act
        String resultado =
                service.cancelar(request);


        // Assert
        assertEquals(
                "Transacción cancelada correctamente",
                resultado
        );


        verify(repository)
                .cancelarTransaccion(
                        1L,
                        "123456"
                );
    }


    @Test
    void rechazarCancelacionConEstatusIncorrecto() {

        // Arrange
        CancelacionRequest request =
                new CancelacionRequest();

        request.setId(1L);
        request.setReferencia("123456");

        request.setEstatus(
                "aprobada"
        );


        // Act + Assert
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.cancelar(request)
                );


        assertEquals(
                "El estatus debe contener el valor cancelar",
                exception.getMessage()
        );


        // Como falló antes, nunca debe llamar al Repository
        verifyNoInteractions(repository);
    }


    @Test
    void listarTransaccionesPaginadas() {

        // Arrange
        Transaccion transaccion =
                new Transaccion();

        transaccion.setId(1L);
        transaccion.setOperacion("venta");
        transaccion.setImporte(
                new BigDecimal("100.00")
        );

        transaccion.setCliente("Angel");
        transaccion.setReferencia("123456");
        transaccion.setEstatus("Aprobada");
        transaccion.setSecreto("secreto");


        Page<Transaccion> pagina =
                new PageImpl<>(
                        List.of(transaccion)
                );


        when(
                repository.findAll(
                        any(Pageable.class)
                )
        ).thenReturn(pagina);


        // Act
        Page<Transaccion> resultado =
                service.listar(
                        0,
                        10,
                        "id"
                );


        // Assert
        assertNotNull(resultado);

        assertEquals(
                1,
                resultado.getTotalElements()
        );

        assertEquals(
                "venta",
                resultado.getContent()
                        .get(0)
                        .getOperacion()
        );


        verify(repository)
                .findAll(
                        any(Pageable.class)
                );
    }
}