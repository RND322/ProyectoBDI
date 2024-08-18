package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Tarjeta;

public interface TarjetaService {
    
    public List<Tarjeta> obtenerTarjetasCliente(String username, String password) throws SQLException;
}
