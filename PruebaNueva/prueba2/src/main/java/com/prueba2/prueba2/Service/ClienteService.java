package com.prueba2.prueba2.Service;

import com.prueba2.prueba2.Entities.Cliente;

public interface  ClienteService {
    
    public Cliente registrarCliente(String dni, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, 
    String correo, Long idEstadoCivil, Long idGenero, String usuario, Long idCiudad, String telefonoHogar, String telefonoCelular);
}