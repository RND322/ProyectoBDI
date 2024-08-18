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
import com.prueba2.prueba2.Service.MarcaService;

@Service
public class MarcaServiceImpl  implements MarcaService{
    
    @Autowired
    private DatabaseConnecction databaseConnection;

    @Override
    public List<Marca> obtenerTodasLasMarcas(String username, String password) throws SQLException {
        String query = "SELECT idmarca, nombremarca FROM central.marca";

        List<Marca> marcas = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Marca marca = new Marca();
                marca.setIdMarca(resultSet.getLong("idmarca"));
                marca.setNombreMarca(resultSet.getString("nombremarca"));

                marcas.add(marca);
            }
        }

        return marcas;
    }
    
}
