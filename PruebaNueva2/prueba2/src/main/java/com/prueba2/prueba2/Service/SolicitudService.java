package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Solicitud;

public interface  SolicitudService {
    
    public void registerSolicitud(Solicitud solicitud, String username, String password) throws SQLException;
    public List<Solicitud> getAllSolicitudes(String username, String password) throws SQLException;
    public Solicitud getSolicitudById(String username, String password, long idSolicitud) throws SQLException;
    public void denegarSolicitud(String username, String password, long idSolicitud) throws SQLException;

    public void aceptarSolicitud(String username, String password, long idSolicitud) throws SQLException;
}
