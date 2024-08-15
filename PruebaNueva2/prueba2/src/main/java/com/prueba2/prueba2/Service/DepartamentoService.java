package com.prueba2.prueba2.Service;

import java.sql.SQLException;
import java.util.List;

import com.prueba2.prueba2.Entities.Departamento;

public interface DepartamentoService {

    List<Departamento> getAllDepartamentos(String username, String password) throws SQLException;
}
