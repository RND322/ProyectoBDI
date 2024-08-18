package com.prueba2.prueba2.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Transaccion;

public interface TransaccionService {
    
    public void generarTransaccion(Long idTarjeta, String concepto, BigDecimal monto, Long idMoneda, String username, String password) throws SQLException;

    public List<Transaccion> obtenerTransaccionesPorTarjeta(Long idTarjeta, String username, String password) throws SQLException;
}
