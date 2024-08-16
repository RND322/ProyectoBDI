package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Entities.Solicitud;
import com.prueba2.prueba2.Service.SolicitudService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class SolicitudController {
    
     @Autowired
    private SolicitudService solicitudService;
/* 
    @PostMapping("/registersolicitud")
    public String registerSolicitud(@RequestBody Solicitud solicitud, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            return "No autenticado"; // Usuario no autenticado
        }
        System.out.println("Received Solicitud: " + solicitud);
        try {
            solicitudService.registerSolicitud(solicitud, username, password);
            return "Solicitud registrada exitosamente";
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            return "Error al registrar la solicitud";
        }
    }
*/
/* 
@PostMapping("/registersolicitud")
public String registerSolicitud(@RequestBody Solicitud solicitud, HttpSession session) {
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (username == null || password == null) {
        return "No autenticado"; // Usuario no autenticado
    }
    System.out.println("Received Solicitud: " + solicitud);
    try {
        solicitudService.registerSolicitud(solicitud, username, password);
        return "Solicitud registrada exitosamente";
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        return "Error al registrar la solicitud";
    }
}
*/

@PostMapping("/registersolicitud")
public String registerSolicitud(@RequestBody Solicitud solicitud, HttpSession session) {
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (username == null || password == null) {
        return "No autenticado"; // Usuario no autenticado
    }
    try {
        solicitudService.registerSolicitud(solicitud, username, password);
        return "Solicitud registrada exitosamente";
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        return "Error al registrar la solicitud";
    }
}

}
