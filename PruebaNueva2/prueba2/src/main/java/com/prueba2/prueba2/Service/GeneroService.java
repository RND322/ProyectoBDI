package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Genero;

public interface GeneroService {
    
    public List<Genero> obtenerTodosLosGeneros(String username, String password) throws SQLException;
}
