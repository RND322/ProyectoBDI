package com.prueba2.prueba2.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba2.prueba2.Entities.Cliente;
import com.prueba2.prueba2.Entities.Direccion;
import com.prueba2.prueba2.Entities.EstadoCivil;
import com.prueba2.prueba2.Entities.Genero;
import com.prueba2.prueba2.Entities.Telefono;
import com.prueba2.prueba2.Repositories.CiudadRepository;
import com.prueba2.prueba2.Repositories.ClienteRepository;
import com.prueba2.prueba2.Repositories.DireccionRepository;
import com.prueba2.prueba2.Repositories.EstadoCivilRepository;
import com.prueba2.prueba2.Repositories.GeneroRepository;
import com.prueba2.prueba2.Repositories.TelefonoRepository;
import com.prueba2.prueba2.Service.ClienteService;

import jakarta.servlet.http.HttpSession;

@Service
public class ClienteServiceImpl implements ClienteService{


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private HttpSession session;

    @Override
    public Cliente registrarCliente(String dni, String primerNombre, String segundoNombre, 
                                    String primerApellido, String segundoApellido, 
                                    String correo, Long idEstadoCivil, Long idGenero, 
                                    String usuario, Long idCiudad, 
                                    String telefonoHogar, String telefonoCelular) {
        // Verifica si el usuario está autenticado
        String sessionUsername = (String) session.getAttribute("username");
        String sessionPassword = (String) session.getAttribute("password");

        if (sessionUsername == null || sessionPassword == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        // Crea el cliente
        Cliente cliente = new Cliente();
        cliente.setDni(dni);
        cliente.setPrimerNombre(primerNombre);
        cliente.setSegundoNombre(segundoNombre);
        cliente.setPrimerApellido(primerApellido);
        cliente.setSegundoApellido(segundoApellido);
        cliente.setCorreo(correo);
        cliente.setUsuarioC(usuario);

        // Asigna Estado Civil y Género
        EstadoCivil estadoCivil = estadoCivilRepository.findById(idEstadoCivil)
                .orElseThrow(() -> new RuntimeException("Estado Civil no encontrado"));
        cliente.setEstadoCivil(estadoCivil);

        Genero genero = generoRepository.findById(idGenero)
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));
        cliente.setGenero(genero);

        // Guarda cliente
        cliente = clienteRepository.save(cliente);

        // Crea y asigna dirección
        Direccion direccion = new Direccion();
        direccion.setCliente(cliente);
        direccion.setCiudad(ciudadRepository.findById(idCiudad)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada")));
        direccionRepository.save(direccion);

        // Crea y asigna teléfono
        Telefono telefono = new Telefono();
        telefono.setCliente(cliente);
        telefono.setTelefonoHogar(telefonoHogar);
        telefono.setTelefonoCelular(telefonoCelular);
        telefonoRepository.save(telefono);

        return cliente;
    }
}
