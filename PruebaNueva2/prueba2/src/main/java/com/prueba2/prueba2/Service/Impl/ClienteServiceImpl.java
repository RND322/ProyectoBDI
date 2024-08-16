package com.prueba2.prueba2.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba2.prueba2.Classes.DatabaseConnecction;
import com.prueba2.prueba2.Entities.Cliente;
import com.prueba2.prueba2.Entities.Direccion;
import com.prueba2.prueba2.Entities.Telefono;
import com.prueba2.prueba2.Service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private DatabaseConnecction databaseConnection;

@Override
public void registerCliente(Cliente cliente, String username, String password) throws SQLException {
    try (Connection connection = databaseConnection.getConnection(username, password)) {
        // Registrar el cliente
        String clienteQuery = "INSERT INTO central.cliente (dni, usuarioc, primernombre, segundonombre, primerapellido, segundoapellido, correo, idestadocivil, idgenero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement clienteStatement = connection.prepareStatement(clienteQuery)) {
            clienteStatement.setString(1, cliente.getDni());
            clienteStatement.setString(2, cliente.getUsuarioC());
            clienteStatement.setString(3, cliente.getPrimerNombre());
            clienteStatement.setString(4, cliente.getSegundoNombre());
            clienteStatement.setString(5, cliente.getPrimerApellido());
            clienteStatement.setString(6, cliente.getSegundoApellido());
            clienteStatement.setString(7, cliente.getCorreo());

            if (cliente.getEstadoCivil() != null) {
                clienteStatement.setLong(8, cliente.getEstadoCivil().getIdEstadoCivil());
            } else {
                clienteStatement.setNull(8, java.sql.Types.BIGINT);
            }

            if (cliente.getGenero() != null) {
                clienteStatement.setLong(9, cliente.getGenero().getIdGenero());
            } else {
                clienteStatement.setNull(9, java.sql.Types.BIGINT);
            }

            clienteStatement.executeUpdate();
        }

        // Registrar la dirección
        for (Direccion direccion : cliente.getDirecciones()) {  
            String direccionQuery = "INSERT INTO central.direccion (idciudad, dni) VALUES (?, ?)";
            try (PreparedStatement direccionStatement = connection.prepareStatement(direccionQuery)) {
                if (direccion.getCiudad() != null) {
                    direccionStatement.setLong(1, direccion.getCiudad().getIdCiudad());
                } else {
                    direccionStatement.setNull(1, java.sql.Types.BIGINT);
                }
                direccionStatement.setString(2, cliente.getDni());
                direccionStatement.executeUpdate();
            }
        }

        // Registrar los teléfonos
        for (Telefono telefono : cliente.getTelefonos()) {
            String telefonoQuery = "INSERT INTO central.telefono (telefonohogar, telefonocelular, dni) VALUES (?, ?, ?)";
            try (PreparedStatement telefonoStatement = connection.prepareStatement(telefonoQuery)) {
                telefonoStatement.setString(1, telefono.getTelefonoHogar());
                telefonoStatement.setString(2, telefono.getTelefonoCelular());
                telefonoStatement.setString(3, cliente.getDni());
                telefonoStatement.executeUpdate();
            }
        }
    }
}

}