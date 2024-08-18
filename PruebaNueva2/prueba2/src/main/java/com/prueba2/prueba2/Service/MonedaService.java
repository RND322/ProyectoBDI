package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Moneda;

public interface MonedaService {
    
     public List<Moneda> obtenerTodasLasMonedas(String username, String password) throws SQLException;
}
