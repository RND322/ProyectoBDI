package com.prueba2.prueba2.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba2.prueba2.Classes.DatabaseConnecction;
import com.prueba2.prueba2.Entities.DatosLaborales;
import com.prueba2.prueba2.Entities.ReferenciasPersonales;
import com.prueba2.prueba2.Entities.Solicitud;
import com.prueba2.prueba2.Service.SolicitudService;

@Service
public class SolicitudServiceImpl implements SolicitudService {
    
     @Autowired
    private DatabaseConnecction databaseConnection;

@Override
public void registerSolicitud(Solicitud solicitud, String username, String password) throws SQLException {
    System.out.println("Iniciando registro de solicitud...");
    try (Connection connection = databaseConnection.getConnection(username, password)) {
        System.out.println("Conexión a la base de datos establecida.");

        // Registrar la solicitud y obtener el idSolicitud generado
        String solicitudQuery = "INSERT INTO central.solicitud (dni, idempleado, idtipoproducto, idestadosoli) VALUES (?, ?, ?, ?)";
        long generatedIdSolicitud = 0;
        try (PreparedStatement solicitudStatement = connection.prepareStatement(solicitudQuery, new String[] {"idsolicitud"})) {
            solicitudStatement.setString(1, solicitud.getCliente().getDni());
            solicitudStatement.setString(2,"2");
            solicitudStatement.setLong(3, solicitud.getTipoProducto() != null ? solicitud.getTipoProducto().getIdTipoProducto() : null);
            solicitudStatement.setLong(4,3);

            System.out.println("Antes de ejecutar la consulta.");
            int rowsAffected = solicitudStatement.executeUpdate();
            System.out.println("Consulta ejecutada con éxito. Filas afectadas: " + rowsAffected);

            // Recuperar el ID generado
            try (ResultSet generatedKeys = solicitudStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedIdSolicitud = generatedKeys.getLong(1);
                    solicitud.setIdSolicitud(generatedIdSolicitud);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para la solicitud.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta de solicitud: " + e.getMessage());
            e.printStackTrace();
        }

        // Registrar los datos laborales, si se proporcionan
        List<DatosLaborales> datosLaboralesList = solicitud.getCliente().getDatosLaborales();
        if (datosLaboralesList != null) {
            for (DatosLaborales datosLaborales : datosLaboralesList) {
                String datosLaboralesQuery = "INSERT INTO central.datoslaborales (cargo, ingresoMensual, nombreLugarTrabajo, fechaIngresoTrabajo, dni) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement datosLaboralesStatement = connection.prepareStatement(datosLaboralesQuery)) {
                    datosLaboralesStatement.setString(1, datosLaborales.getCargo());
                    datosLaboralesStatement.setBigDecimal(2, datosLaborales.getIngresoMensual());
                    datosLaboralesStatement.setString(3, datosLaborales.getNombreLugarTrabajo());
                    datosLaboralesStatement.setDate(4, new java.sql.Date(datosLaborales.getFechaIngresoTrabajo().getTime()));
                    datosLaboralesStatement.setString(5, solicitud.getCliente().getDni());
                    datosLaboralesStatement.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error al registrar datos laborales: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // Registrar las referencias personales, si se proporcionan
        List<ReferenciasPersonales> referenciasPersonalesList = solicitud.getReferenciasPersonales();
        if (referenciasPersonalesList != null) {
            for (ReferenciasPersonales referencia : referenciasPersonalesList) {
                String referenciasQuery = "INSERT INTO central.referenciaspersonales (primernombre, primerapellido, telresi, telcel, relacionSolicitante, idsolicitud) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement referenciasStatement = connection.prepareStatement(referenciasQuery)) {
                    referenciasStatement.setString(1, referencia.getPrimerNombre());
                    referenciasStatement.setString(2, referencia.getPrimerApellido());
                    referenciasStatement.setString(3, referencia.getTelResi());
                    referenciasStatement.setString(4, referencia.getTelCel());
                    referenciasStatement.setString(5, referencia.getRelacionSolicitante());
                    referenciasStatement.setLong(6, solicitud.getIdSolicitud());
                    referenciasStatement.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error al registrar referencias personales: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
        e.printStackTrace();
    }
}




}
