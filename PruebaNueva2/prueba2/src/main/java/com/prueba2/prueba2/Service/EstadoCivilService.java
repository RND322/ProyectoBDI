package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.EstadoCivil;

public interface EstadoCivilService {
    
    public List<EstadoCivil> obtenerTodosLosEstadosCiviles(String username, String password) throws SQLException;
}
