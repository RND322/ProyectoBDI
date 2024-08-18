package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Entities.Marca;
import com.prueba2.prueba2.Service.MarcaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class MarcaController {
    
    @Autowired
    private MarcaService marcaService;

    @GetMapping("/marcas")
    public List<Marca> getAllMarcas(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            throw new IllegalStateException("Username or password not found in session");
        }

        try {
            return marcaService.obtenerTodasLasMarcas(username, password);
        } catch (SQLException e) {
            e.printStackTrace(); // Log 
        }
        return Collections.emptyList(); // Retorna una cadena vacia en caso de error.
    }
}
