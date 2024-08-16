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
import com.prueba2.prueba2.Entities.Departamento;
import com.prueba2.prueba2.Service.DepartamentoService;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DatabaseConnecction databaseConnection;

@Override
    public List<Departamento> getAllDepartamentos(String username, String password) throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();

        try (Connection connection = databaseConnection.getConnection(username, password)) {
            String queryDepartamentos = "SELECT * FROM central.departamento";
            try (PreparedStatement preparedStatementDept = connection.prepareStatement(queryDepartamentos)) {
                ResultSet resultSetDept = preparedStatementDept.executeQuery();

                while (resultSetDept.next()) {
                    Departamento departamento = new Departamento();
                    departamento.setIdDepto(resultSetDept.getLong("iddepto"));
                    departamento.setNombreDepto(resultSetDept.getString("nombredepto"));

                    // Ahora obtener las ciudades asociadas a este departamento
                    Long deptoId = resultSetDept.getLong("iddepto");
                    String queryCiudades = "SELECT * FROM principal.ciudad WHERE iddepto = ?";
                    try (PreparedStatement preparedStatementCiudad = connection.prepareStatement(queryCiudades)) {
                        preparedStatementCiudad.setLong(1, deptoId);
                        ResultSet resultSetCiudad = preparedStatementCiudad.executeQuery();

                        List<Ciudad> ciudades = new ArrayList<>();
                        while (resultSetCiudad.next()) {
                            Ciudad ciudad = new Ciudad();
                            ciudad.setIdCiudad(resultSetCiudad.getLong("idciudad"));
                            ciudad.setNombreCiudad(resultSetCiudad.getString("nombreciudad"));
                            ciudad.setDepartamento(departamento); // Establecer el departamento de la ciudad

                            ciudades.add(ciudad);
                        }
                        departamento.setCiudades(ciudades);
                    }

                    departamentos.add(departamento);
                }
            }
        }
        return departamentos;
    }

}
