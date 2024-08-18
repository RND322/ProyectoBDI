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
import com.prueba2.prueba2.Entities.EstadoCivil;
import com.prueba2.prueba2.Service.EstadoCivilService;

@Service
public class EstadoCivilServiceImpl implements EstadoCivilService{

    @Autowired
    private DatabaseConnecction databaseConnection;

    @Override
    public List<EstadoCivil> obtenerTodosLosEstadosCiviles(String username, String password) throws SQLException {
        String query = "SELECT idestadocivil, descripestadocivil FROM central.estadocivil";

        List<EstadoCivil> estadosCiviles = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                EstadoCivil estadoCivil = new EstadoCivil();
                estadoCivil.setIdEstadoCivil(resultSet.getLong("idestadocivil"));
                estadoCivil.setDescripEstadoCivil(resultSet.getString("descripestadocivil"));

                estadosCiviles.add(estadoCivil);
            }
        }

        return estadosCiviles;
    }
    
}
