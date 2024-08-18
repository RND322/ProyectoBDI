package com.prueba2.prueba2.Classes;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransaccionRequest {
    private Long idTarjeta;
    private String concepto;
    private BigDecimal monto;
    private Long idMoneda;
}