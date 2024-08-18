package com.prueba2.prueba2.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba2.prueba2.Classes.DatabaseConnecction;
import com.prueba2.prueba2.Entities.Marca;
import com.prueba2.prueba2.Entities.Tarjeta;
import com.prueba2.prueba2.Entities.TipoProducto;
import com.prueba2.prueba2.Service.TarjetaService;

@Service
public class TarjetaServiceImpl implements TarjetaService {
    
    @Autowired
    private DatabaseConnecction databaseConnection;

    @Override
public List<Tarjeta> obtenerTarjetasCliente(String username, String password) throws SQLException {
    String queryDniCliente = "SELECT dni FROM central.cliente WHERE usuarioc = ?";
    String queryTarjetas = "SELECT t.idtarjeta, t.fechaapertura, t.fechavencimiento, t.saldoactual, " +
                           "t.saldodisponible, t.pagominimo, t.tasainteresanual, tp.nombreproducto, " +
                           "tp.limitecredito, m.nombremarca " +
                           "FROM central.tarjeta t " +
                           "LEFT JOIN central.tipoproducto tp ON t.idtipoproducto = tp.idtipoproducto " +
                           "LEFT JOIN central.marca m ON tp.idmarca = m.idmarca " +
                           "WHERE t.dni = ?";

    List<Tarjeta> tarjetas = new ArrayList<>();

    try (Connection connection = databaseConnection.getConnection(username, password)) {
        String dniCliente = null;

        // Obtener el DNI del cliente basado en el usuario
        try (PreparedStatement statementDni = connection.prepareStatement(queryDniCliente)) {
            statementDni.setString(1, username);
            try (ResultSet resultSetDni = statementDni.executeQuery()) {
                if (resultSetDni.next()) {
                    dniCliente = resultSetDni.getString("dni");
                } else {
                    throw new SQLException("Cliente no encontrado");
                }
            }
        }

        // Obtener las tarjetas del cliente
        try (PreparedStatement statementTarjetas = connection.prepareStatement(queryTarjetas)) {
            statementTarjetas.setString(1, dniCliente);
            try (ResultSet resultSetTarjetas = statementTarjetas.executeQuery()) {
                while (resultSetTarjetas.next()) {
                    Tarjeta tarjeta = new Tarjeta();
                    tarjeta.setIdTarjeta(resultSetTarjetas.getLong("idtarjeta"));
                    tarjeta.setFechaApertura(resultSetTarjetas.getDate("fechaapertura"));
                    tarjeta.setFechaVencimiento(resultSetTarjetas.getDate("fechavencimiento"));
                    tarjeta.setSaldoActual(resultSetTarjetas.getBigDecimal("saldoactual"));
                    tarjeta.setSaldoDisponible(resultSetTarjetas.getBigDecimal("saldodisponible"));
                    tarjeta.setPagoMinimo(resultSetTarjetas.getBigDecimal("pagominimo"));
                    tarjeta.setTasaInteresAnual(resultSetTarjetas.getBigDecimal("tasainteresanual"));

                    // Crear la entidad TipoProducto y asociarla con la tarjeta
                    TipoProducto tipoProducto = new TipoProducto();
                    tipoProducto.setNombreProducto(resultSetTarjetas.getString("nombreproducto"));
                    tipoProducto.setLimiteCredito(resultSetTarjetas.getBigDecimal("limitecredito"));

                    // Crear la entidad Marca y asociarla con el TipoProducto
                    Marca marca = new Marca();
                    marca.setNombreMarca(resultSetTarjetas.getString("nombremarca"));
                    tipoProducto.setMarca(marca);

                    tarjeta.setTipoProducto(tipoProducto);

                    tarjetas.add(tarjeta);
                }
            }
        }

        return tarjetas;
    }
}
}
