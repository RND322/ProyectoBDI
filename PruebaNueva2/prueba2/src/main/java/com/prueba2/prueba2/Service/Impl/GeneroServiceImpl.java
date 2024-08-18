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
import com.prueba2.prueba2.Entities.Genero;
import com.prueba2.prueba2.Service.GeneroService;

@Service
public class GeneroServiceImpl implements GeneroService {
    
    @Autowired
    private DatabaseConnecction databaseConnection;

    @Override
    public List<Genero> obtenerTodosLosGeneros(String username, String password) throws SQLException {
        String query = "SELECT idgenero, descripgenero FROM central.genero";

        List<Genero> generos = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Genero genero = new Genero();
                genero.setIdGenero(resultSet.getLong("idgenero"));
                genero.setDescripGenero(resultSet.getString("descripgenero"));

                generos.add(genero);
            }
        }

        return generos;
    }
    
}
