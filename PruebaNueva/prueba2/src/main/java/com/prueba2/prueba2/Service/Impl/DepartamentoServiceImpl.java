package com.prueba2.prueba2.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.prueba2.prueba2.Entities.Departamento;
import com.prueba2.prueba2.Repositories.DepartamentoRepository;
import com.prueba2.prueba2.Service.DepartamentoService;

public class DepartamentoServiceImpl implements DepartamentoService {
    
    @Autowired
    DepartamentoRepository departamentoRepository;

      @Override
    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id).orElse(null);
    }

}
