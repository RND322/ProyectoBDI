package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Entities.Ciudad;
import com.prueba2.prueba2.Service.CiudadService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class CiudadController {
    
    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/ciudades")
    public List<Ciudad> getAllCiudades(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            throw new IllegalStateException("Username or password not found in session");
        }

        try {
            return ciudadService.obtenerTodasLasCiudades(username, password);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return Collections.emptyList(); // Returna lista vacia en caso de error.
    }

}

