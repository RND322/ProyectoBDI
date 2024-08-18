package com.prueba2.prueba2.Controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Classes.TransaccionRequest;
import com.prueba2.prueba2.Entities.Transaccion;
import com.prueba2.prueba2.Service.Impl.TransaccionServiceImpl;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class TransaccionController {

    @Autowired
    private TransaccionServiceImpl transaccionService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearTransaccion(@RequestBody TransaccionRequest request, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            return new ResponseEntity<>("No autenticado", HttpStatus.UNAUTHORIZED);
        }

        try {
            transaccionService.generarTransaccion(
                request.getIdTarjeta(),
                request.getConcepto(),
                request.getMonto(),
                request.getIdMoneda(),
                username,
                password
            );
            return new ResponseEntity<>("Transacción realizada con éxito", HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al realizar la transacción", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tarjeta/{idTarjeta}")
    public ResponseEntity<?> obtenerTransaccionesPorTarjeta(@PathVariable Long idTarjeta, HttpSession session) {
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (username == null || password == null) {
        return new ResponseEntity<>("No autenticado", HttpStatus.UNAUTHORIZED);
    }

    try {
        List<Transaccion> transacciones = transaccionService.obtenerTransaccionesPorTarjeta(idTarjeta, username, password);

        if (transacciones != null && !transacciones.isEmpty()) {
            return new ResponseEntity<>(transacciones, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontraron transacciones", HttpStatus.NOT_FOUND);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        return new ResponseEntity<>("Error al obtener las transacciones", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
