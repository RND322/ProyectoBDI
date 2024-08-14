package com.prueba2.prueba2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba2.prueba2.Entities.Cliente;
import com.prueba2.prueba2.Service.ClienteService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/banco")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private HttpSession session;

    @PostMapping("/registrarcliente")
    public ResponseEntity<Object> registrarCliente(@RequestParam String dni, @RequestParam String primerNombre, @RequestParam String segundoNombre, 
                                                    @RequestParam String primerApellido, @RequestParam String segundoApellido, 
                                                    @RequestParam String correo, @RequestParam Long idEstadoCivil, @RequestParam Long idGenero, 
                                                    @RequestParam String usuario, @RequestParam Long idCiudad, 
                                                    @RequestParam String telefonoHogar, @RequestParam String telefonoCelular) {
        String sessionUsername = (String) session.getAttribute("username");
        String sessionPassword = (String) session.getAttribute("password");

        if (sessionUsername == null || sessionPassword == null) {
            return new ResponseEntity<>("Usuario no autenticado", HttpStatus.UNAUTHORIZED);
        }

        try {
            Cliente cliente = clienteService.registrarCliente(dni, primerNombre, segundoNombre, primerApellido, segundoApellido, 
                                                              correo, idEstadoCivil, idGenero, usuario, idCiudad, telefonoHogar, 
                                                              telefonoCelular);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
   
}
