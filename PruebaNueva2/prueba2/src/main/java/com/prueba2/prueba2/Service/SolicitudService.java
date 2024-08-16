package com.prueba2.prueba2.Service;

import java.sql.SQLException;

import com.prueba2.prueba2.Entities.Solicitud;

public interface  SolicitudService {
    
     
    public void registerSolicitud(Solicitud solicitud, String username, String password) throws SQLException;
    

}
