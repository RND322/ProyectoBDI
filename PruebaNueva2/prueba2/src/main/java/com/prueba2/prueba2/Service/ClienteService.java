package com.prueba2.prueba2.Service;

import java.sql.SQLException;

import com.prueba2.prueba2.Entities.Cliente;

public interface ClienteService {
    
    public void registerCliente(Cliente cliente, String username, String password) throws SQLException;
}
