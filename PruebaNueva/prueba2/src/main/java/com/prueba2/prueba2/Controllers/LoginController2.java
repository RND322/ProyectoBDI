package com.prueba2.prueba2.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Classes.DatabaseConnecction;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/banco")
public class LoginController2 {
    
    @Autowired
    private DatabaseConnecction databaseConnection;

    @PostMapping("/logincliente")
    public String loginCliente(@RequestParam String usuarioC, @RequestParam String password, HttpSession session) {
        if (authenticate("cliente", usuarioC, password)) {
            session.setAttribute("username", usuarioC);
            session.setAttribute("userType", "cliente");
            return "Login successful";
        } else {
            return "Invalid username or password for cliente";
        }
    }

    @PostMapping("/loginempleado")
    public String loginEmpleado(@RequestParam String usuarioE, @RequestParam String password, HttpSession session) {
        if (authenticate("empleado", usuarioE, password)) {
            session.setAttribute("username", usuarioE);
            session.setAttribute("userType", "empleado");
            return "Login successful";
        } else {
            return "Invalid username or password for empleado";
        }
    }

    @GetMapping("/clientedata")
    public String getClienteData(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || !"cliente".equals(session.getAttribute("userType"))) {
            return "Unauthorized";
        }

        try (Connection connection = databaseConnection.getConnection(username, null)) {
            String query = "SELECT * FROM cliente WHERE usuarioc = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return "Cliente Data: " +
                               "Nombre: " + resultSet.getString("primernombre") + " " + resultSet.getString("segundonombre") + ", " +
                               "Apellido: " + resultSet.getString("primerapellido") + " " + resultSet.getString("segundoapellido") + ", " +
                               "Correo: " + resultSet.getString("correo");
                    } else {
                        return "Cliente not found";
                    }
                }
            }
        } catch (SQLException e) {
            return "Error retrieving cliente data: " + e.getMessage();
        }
    }

    @GetMapping("/empleadodata")
    public String getEmpleadoData(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || !"empleado".equals(session.getAttribute("userType"))) {
            return "Unauthorized";
        }

        try (Connection connection = databaseConnection.getConnection(username, null)) {
            String query = "SELECT * FROM empleado WHERE usuarioe = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return "Empleado Data: " +
                               "Nombre: " + resultSet.getString("primernombre") + ", " +
                               "Apellido: " + resultSet.getString("primerapellido") + ", " +
                               "Cargo: " + resultSet.getString("cargo");
                    } else {
                        return "Empleado not found";
                    }
                }
            }
        } catch (SQLException e) {
            return "Error retrieving empleado data: " + e.getMessage();
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logout successful";
    }

    private boolean authenticate(String userType, String username, String password) {
        try (Connection connection = databaseConnection.getConnection(username, password)) {
            String query = "SELECT 1"; // Dummy query just to check the credentials
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }
}

