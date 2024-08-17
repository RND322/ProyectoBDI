//Agregada el 12/8/24
package com.prueba2.prueba2.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.prueba2.prueba2.Classes.DatabaseConnecction;
import com.prueba2.prueba2.Entities.Ciudad;
import com.prueba2.prueba2.Entities.Cliente;
import com.prueba2.prueba2.Entities.Direccion;
import com.prueba2.prueba2.Entities.Empleado;
import com.prueba2.prueba2.Entities.EstadoCivil;
import com.prueba2.prueba2.Entities.Genero;
import com.prueba2.prueba2.Entities.Sucursal;
import com.prueba2.prueba2.Entities.Telefono;

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

  @PostMapping("/logincliente")
    public Cliente logincliente(@RequestParam String username, @RequestParam String password, HttpSession session) {
        String query = "SELECT c.dni, c.primernombre, c.segundonombre, c.primerapellido, c.segundoapellido, " +
                       "c.correo, ec.descripestadocivil, g.descripgenero " +
                       "FROM central.cliente c " +
                       "LEFT JOIN central.estadocivil ec ON c.idestadocivil = ec.idestadocivil " +
                       "LEFT JOIN central.genero g ON c.idgenero = g.idgenero " +
                       "WHERE c.usuarioc = ?";
        
        String queryDirecciones = "SELECT d.iddireccion, d.idciudad, d.dni, c.nombreciudad " +
                                  "FROM central.direccion d " +
                                  "LEFT JOIN central.ciudad c ON d.idciudad = c.idciudad " +
                                  "WHERE d.dni = ?";
        
        String queryTelefonos = "SELECT t.idtelefono, t.telefonohogar, t.telefonocelular " +
                                "FROM central.telefono t " +
                                "WHERE t.dni = ?";

        Cliente cliente = null;

        try (Connection connection = databaseConnection.getConnection(username, password)) {
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            // Obtener datos del cliente
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                //System.out.println("Ejecutando consulta para el usuario: " + username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        cliente = new Cliente();
                        cliente.setDni(resultSet.getString("dni"));
                        //cliente.setUsuarioC(resultSet.getString("usuarioc"));
                        cliente.setPrimerNombre(resultSet.getString("primernombre"));
                        cliente.setSegundoNombre(resultSet.getString("segundonombre"));
                        cliente.setPrimerApellido(resultSet.getString("primerapellido"));
                        cliente.setSegundoApellido(resultSet.getString("segundoapellido"));
                        cliente.setCorreo(resultSet.getString("correo"));
                        
                        EstadoCivil estadoCivil = new EstadoCivil();
                        estadoCivil.setDescripEstadoCivil(resultSet.getString("descripestadocivil"));
                        cliente.setEstadoCivil(estadoCivil);
                        
                        Genero genero = new Genero();
                        genero.setDescripGenero(resultSet.getString("descripgenero"));
                        cliente.setGenero(genero);
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron datos para el usuario: " + username);
                    }
                }
            }

            // Obtener direcciones
            try (PreparedStatement statement = connection.prepareStatement(queryDirecciones)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Direccion> direcciones = new ArrayList<>();
                    while (resultSet.next()) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getLong("iddireccion"));

                        // Configura la ciudad
                        Ciudad ciudad = new Ciudad();
                        ciudad.setIdCiudad(resultSet.getLong("idciudad"));
                        ciudad.setNombreCiudad(resultSet.getString("nombreciudad"));

                        direccion.setCiudad(ciudad);
                        direccion.setCliente(cliente);
                        direcciones.add(direccion);
                    }
                    cliente.setDirecciones(direcciones);
                }
            }

            // Obtener teléfonos
            try (PreparedStatement statement = connection.prepareStatement(queryTelefonos)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Telefono> telefonos = new ArrayList<>();
                    while (resultSet.next()) {
                        Telefono telefono = new Telefono();
                        telefono.setIdTelefono(resultSet.getLong("idtelefono"));
                        telefono.setTelefonoHogar(resultSet.getString("telefonohogar"));
                        telefono.setTelefonoCelular(resultSet.getString("telefonocelular"));
                        telefono.setCliente(cliente);
                        telefonos.add(telefono);
                    }
                    cliente.setTelefonos(telefonos);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la solicitud");
        }

        return cliente;
    }

@PostMapping("/loginempleado")
public ResponseEntity<?> loginEmpleado(@RequestParam String username, @RequestParam String password, HttpSession session) {
    // Consulta SQL para obtener los datos del empleado
    String queryEmpleado = "SELECT e.idempleado, e.usuarioe, e.primernombre, e.primerapellido, e.cargo, s.idsucursal, s.nombresucursal " +
                           "FROM central.empleado e " +
                           "LEFT JOIN central.sucursal s ON e.idsucursal = s.idsucursal " +
                           "WHERE e.usuarioe = ?";

    Empleado empleado = null;

    try (Connection connection = databaseConnection.getConnection(username, password)) {
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        // Obtener datos del empleado
        try (PreparedStatement statement = connection.prepareStatement(queryEmpleado)) {
            statement.setString(1, username);
            System.out.println("Ejecutando consulta para el empleado: " + username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    empleado = new Empleado();
                    empleado.setIdEmpleado(resultSet.getString("idempleado"));
                    //empleado.setUsuarioE(resultSet.getString("usuarioe"));
                    empleado.setPrimerNombre(resultSet.getString("primernombre"));
                    empleado.setPrimerApellido(resultSet.getString("primerapellido"));
                    empleado.setCargo(resultSet.getString("cargo"));

                    // Configurar la sucursal
                    Sucursal sucursal = new Sucursal();
                    sucursal.setIdSucursal(resultSet.getLong("idsucursal"));
                    sucursal.setNombreSucursal(resultSet.getString("nombresucursal"));

                    empleado.setSucursal(sucursal);
                } else {
                    return new ResponseEntity<>("No se encontraron datos para el empleado: " + username, HttpStatus.NOT_FOUND);
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(empleado, HttpStatus.OK);
}



}

