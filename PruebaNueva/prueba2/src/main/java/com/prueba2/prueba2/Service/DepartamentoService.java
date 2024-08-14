package com.prueba2.prueba2.Service;

import java.util.List;

import com.prueba2.prueba2.Entities.Departamento;

public interface DepartamentoService {

    List<Departamento> getAllDepartamentos();

    Departamento getDepartamentoById(Long id);
    
}
