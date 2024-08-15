//Agregada el 12/8/24
package com.prueba2.prueba2.Controllers;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Classes.DatabaseConnecction;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
public class LoginController {
    
@Autowired
private DatabaseConnecction databaseConnection;

@PostMapping("/login")
public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
    try (Connection connection = databaseConnection.getConnection(username, password)) {
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        return "Login successful";
    } catch (SQLException e) {
        return "Invalid username or password";
    }
}

@PostMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();
    return "Logout successful";
}

}

