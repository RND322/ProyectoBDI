package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Marca;

public interface MarcaService {
    
     public List<Marca> obtenerTodasLasMarcas(String username, String password) throws SQLException;
}
