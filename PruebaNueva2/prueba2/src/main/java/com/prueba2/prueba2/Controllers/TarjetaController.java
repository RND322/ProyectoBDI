package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Entities.Tarjeta;
import com.prueba2.prueba2.Service.TarjetaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class TarjetaController {
    
     @Autowired
    private TarjetaService tarjetaService;

    @GetMapping("/tarjetas")
    public ResponseEntity<?> listarTarjetas(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            return new ResponseEntity<>("No autenticado", HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasCliente(username, password);

            if (tarjetas.isEmpty()) {
                return new ResponseEntity<>("No se encontraron tarjetas para el cliente", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(tarjetas, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
