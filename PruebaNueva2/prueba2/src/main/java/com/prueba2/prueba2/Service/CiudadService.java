package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Ciudad;

public interface  CiudadService{
    
    public List<Ciudad> obtenerTodasLasCiudades(String username, String password) throws SQLException;
}
