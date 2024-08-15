package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Entities.Cliente;
import com.prueba2.prueba2.Service.ClienteService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ClienteController {

     @Autowired
    private ClienteService clienteService;

    @PostMapping("/register")
    public String registerCliente(@RequestBody Cliente cliente, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            return "No autenticado"; // Usuario no autenticado
        }
        System.out.println("Received Cliente: " + cliente);
        try {
            clienteService.registerCliente(cliente, username, password);
            return "Cliente registrado exitosamente";
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            return "Error al registrar el cliente";
        }
    }
    
}
