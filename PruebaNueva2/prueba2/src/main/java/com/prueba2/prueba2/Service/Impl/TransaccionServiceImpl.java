package com.prueba2.prueba2.Service.Impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba2.prueba2.Classes.DatabaseConnecction;
import com.prueba2.prueba2.Entities.Moneda;
import com.prueba2.prueba2.Entities.Transaccion;
import com.prueba2.prueba2.Service.TransaccionService;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private DatabaseConnecction databaseConnection;

    @Override
    public void generarTransaccion(Long idTarjeta, String concepto, BigDecimal monto, Long idMoneda, String username, String password) throws SQLException {
        String queryTarjeta = "SELECT saldodisponible FROM central.tarjeta WHERE idtarjeta = ?";
        String updateTarjeta = "UPDATE central.tarjeta SET saldodisponible = ? WHERE idtarjeta = ?";
        String insertTransaccion = "INSERT INTO central.transaccion (noreferencia, fechatransaccion, concepto, monto, idmoneda) VALUES (?, ?, ?, ?, ?)";
        String insertHistorial = "INSERT INTO central.historialtransaccion (idtarjeta, idtransac) VALUES (?, ?)";

        try (Connection connection = databaseConnection.getConnection(username, password)) {
            connection.setAutoCommit(false);  // Begin transaction

            BigDecimal saldoDisponible = null;

            // Obtener el saldo disponible de la tarjeta
            try (PreparedStatement statementTarjeta = connection.prepareStatement(queryTarjeta)) {
                statementTarjeta.setLong(1, idTarjeta);
                try (ResultSet resultSetTarjeta = statementTarjeta.executeQuery()) {
                    if (resultSetTarjeta.next()) {
                        saldoDisponible = resultSetTarjeta.getBigDecimal("saldodisponible");
                    } else {
                        throw new SQLException("Tarjeta no encontrada");
                    }
                }
            }

            if (saldoDisponible.compareTo(monto) < 0) {
                throw new SQLException("Saldo insuficiente");
            }

            // Actualizar el saldo disponible de la tarjeta
            BigDecimal nuevoSaldoDisponible = saldoDisponible.subtract(monto);
            try (PreparedStatement statementUpdateTarjeta = connection.prepareStatement(updateTarjeta)) {
                statementUpdateTarjeta.setBigDecimal(1, nuevoSaldoDisponible);
                statementUpdateTarjeta.setLong(2, idTarjeta);
                statementUpdateTarjeta.executeUpdate();
            }

            // Insertar la transacción
            Long idTransac;
            String noReferencia = UUID.randomUUID().toString().substring(0, 15);  // Genera un UUID corto como referencia
            try (PreparedStatement statementInsertTransaccion = connection.prepareStatement(insertTransaccion, new String[] {"idtransac"})) {
                statementInsertTransaccion.setString(1, noReferencia);
                statementInsertTransaccion.setDate(2, new java.sql.Date(new Date().getTime()));
                statementInsertTransaccion.setString(3, concepto);
                statementInsertTransaccion.setBigDecimal(4, monto);
                statementInsertTransaccion.setLong(5, idMoneda);
                statementInsertTransaccion.executeUpdate();

                try (ResultSet generatedKeys = statementInsertTransaccion.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idTransac = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("No se pudo obtener el ID de la transacción");
                    }
                }
            }

            // Insertar en el historial de transacciones
            try (PreparedStatement statementInsertHistorial = connection.prepareStatement(insertHistorial)) {
                statementInsertHistorial.setLong(1, idTarjeta);
                statementInsertHistorial.setLong(2, idTransac);
                statementInsertHistorial.executeUpdate();
            }

            connection.commit();  // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al realizar la transacción", e);
        }
    }

    @Override
    public List<Transaccion> obtenerTransaccionesPorTarjeta(Long idTarjeta, String username, String password) throws SQLException {
        String query = "SELECT t.idtransac, t.noreferencia, t.fechatransaccion, t.concepto, t.monto, m.nombremoneda " +
                       "FROM central.transaccion t " +
                       "JOIN central.historialtransaccion ht ON t.idtransac = ht.idtransac " +
                       "JOIN central.moneda m ON t.idmoneda = m.idmoneda " +
                       "WHERE ht.idtarjeta = ?";

        List<Transaccion> transacciones = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, idTarjeta);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Transaccion transaccion = new Transaccion();
                    transaccion.setIdTransac(resultSet.getLong("idtransac"));
                    transaccion.setNoReferencia(resultSet.getString("noreferencia"));
                    transaccion.setFechaTransaccion(resultSet.getDate("fechatransaccion"));
                    transaccion.setConcepto(resultSet.getString("concepto"));
                    transaccion.setMonto(resultSet.getBigDecimal("monto"));

                    // Crear la moneda y asignar a la transacción
                    Moneda moneda = new Moneda();
                    moneda.setNombreMoneda(resultSet.getString("nombremoneda"));
                    transaccion.setMoneda(moneda);

                    transacciones.add(transaccion);
                }
            }
        }

        return transacciones;
    }
}
    
