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
import com.prueba2.prueba2.Entities.Ciudad;
import com.prueba2.prueba2.Service.CiudadService;

@Service
public class CiudadServiceImpl  implements CiudadService{

     @Autowired
    private DatabaseConnecction databaseConnection;

    @Override
    public List<Ciudad> obtenerTodasLasCiudades(String username, String password) throws SQLException {
        String query = "SELECT idciudad, nombreciudad FROM central.ciudad";

        List<Ciudad> ciudades = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setIdCiudad(resultSet.getLong("idciudad"));
                ciudad.setNombreCiudad(resultSet.getString("nombreciudad"));
                ciudades.add(ciudad);
            }
        }

        return ciudades;
    }
    
}
