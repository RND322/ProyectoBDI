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
import com.prueba2.prueba2.Entities.Moneda;
import com.prueba2.prueba2.Service.MonedaService;

@Service
public class MonedaServiceImpl implements MonedaService{

    @Autowired
    private DatabaseConnecction databaseConnection;

    @Override
    public List<Moneda> obtenerTodasLasMonedas(String username, String password) throws SQLException {
        String query = "SELECT idmoneda, nombremoneda FROM central.moneda";

        List<Moneda> monedas = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Moneda moneda = new Moneda();
                moneda.setIdMoneda(resultSet.getLong("idmoneda"));
                moneda.setNombreMoneda(resultSet.getString("nombremoneda"));

                monedas.add(moneda);
            }
        }

        return monedas;
    }
    
}
