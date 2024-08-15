package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Entities.Departamento;
import com.prueba2.prueba2.Service.DepartamentoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class DepartamentoController {
    
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/departamentos")
    public List<Departamento> getAllDepartamentos(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            throw new IllegalStateException("Username or password not found in session");
        }

        try {
            return departamentoService.getAllDepartamentos(username, password);
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
        return Collections.emptyList(); // Devuelve una lista vacía en caso de error
    }
    
}
