package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/solicitudes")
    public ResponseEntity<?> getAllSolicitudes(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            return new ResponseEntity<>("No autenticado", HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Solicitud> solicitudes = solicitudService.getAllSolicitudes(username, password);
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            return new ResponseEntity<>("Error al obtener las solicitudes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

@GetMapping("/solicitud/{idSolicitud}")
public ResponseEntity<?> getSolicitudById(@PathVariable Long idSolicitud, HttpSession session) {
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (username == null || password == null) {
        return new ResponseEntity<>("No autenticado", HttpStatus.UNAUTHORIZED);
    }

    try {
        Solicitud solicitud = solicitudService.getSolicitudById(username, password, idSolicitud);
        
        if (solicitud != null) {
            return new ResponseEntity<>(solicitud, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Solicitud no encontrada", HttpStatus.NOT_FOUND);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        return new ResponseEntity<>("Error al obtener la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PutMapping("/solicitud/denegar/{idSolicitud}")
public ResponseEntity<?> denegarSolicitud(@PathVariable Long idSolicitud, HttpSession session) {
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (username == null || password == null) {
        return new ResponseEntity<>("No autenticado", HttpStatus.UNAUTHORIZED);
    }

    try {
        solicitudService.denegarSolicitud(username, password, idSolicitud);
        return new ResponseEntity<>("Solicitud denegada con éxito", HttpStatus.OK);
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        return new ResponseEntity<>("Error al denegar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PostMapping("/solicitud/aceptar/{idSolicitud}")
public ResponseEntity<?> aceptarSolicitud(@PathVariable Long idSolicitud, HttpSession session) {
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (username == null || password == null) {
        return new ResponseEntity<>("No autenticado", HttpStatus.UNAUTHORIZED);
    }

    try {
        // Llama al servicio para aceptar la solicitud
        solicitudService.aceptarSolicitud(username, password, idSolicitud);
        return new ResponseEntity<>("Solicitud aceptada y tarjeta creada", HttpStatus.OK);
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        return new ResponseEntity<>("Error al aceptar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@GetMapping("/solicitudescliente")
public ResponseEntity<List<Solicitud>> obtenerSolicitudesCliente(HttpSession session) {
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (username == null || password == null) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    try {
        List<Solicitud> solicitudes = solicitudService.obtenerSolicitudesCliente(username, password);
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


}
