package com.prueba2.prueba2.Service.Impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba2.prueba2.Classes.DatabaseConnecction;
import com.prueba2.prueba2.Entities.Cliente;
import com.prueba2.prueba2.Entities.DatosLaborales;
import com.prueba2.prueba2.Entities.Empleado;
import com.prueba2.prueba2.Entities.EstadoSolicitud;
import com.prueba2.prueba2.Entities.Marca;
import com.prueba2.prueba2.Entities.ReferenciasPersonales;
import com.prueba2.prueba2.Entities.Solicitud;
import com.prueba2.prueba2.Entities.TipoProducto;
import com.prueba2.prueba2.Service.SolicitudService;

@Service
public class SolicitudServiceImpl implements SolicitudService {
    
    @Autowired
    private DatabaseConnecction databaseConnection;

@Override
public void registerSolicitud(Solicitud solicitud, String username, String password) throws SQLException {
    System.out.println("Iniciando registro de solicitud...");
    try (Connection connection = databaseConnection.getConnection(username, password)) {
        System.out.println("Conexión a la base de datos establecida.");

        // Registrar la solicitud y obtener el idSolicitud generado
        String solicitudQuery = "INSERT INTO central.solicitud (dni, idempleado, idtipoproducto, idestadosoli) VALUES (?, ?, ?, ?)";
        long generatedIdSolicitud = 0;
        try (PreparedStatement solicitudStatement = connection.prepareStatement(solicitudQuery, new String[] {"idsolicitud"})) {
            solicitudStatement.setString(1, solicitud.getCliente().getDni());
            solicitudStatement.setString(2,"2");
            solicitudStatement.setLong(3, solicitud.getTipoProducto() != null ? solicitud.getTipoProducto().getIdTipoProducto() : null);
            solicitudStatement.setLong(4,3);

            System.out.println("Antes de ejecutar la consulta.");
            int rowsAffected = solicitudStatement.executeUpdate();
            System.out.println("Consulta ejecutada con éxito. Filas afectadas: " + rowsAffected);

            // Recuperar el ID generado
            try (ResultSet generatedKeys = solicitudStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedIdSolicitud = generatedKeys.getLong(1);
                    solicitud.setIdSolicitud(generatedIdSolicitud);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para la solicitud.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta de solicitud: " + e.getMessage());
            e.printStackTrace();
        }

        // Registrar los datos laborales, si se proporcionan
        List<DatosLaborales> datosLaboralesList = solicitud.getCliente().getDatosLaborales();
        if (datosLaboralesList != null) {
            for (DatosLaborales datosLaborales : datosLaboralesList) {
                String datosLaboralesQuery = "INSERT INTO central.datoslaborales (cargo, ingresoMensual, nombreLugarTrabajo, fechaIngresoTrabajo, dni) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement datosLaboralesStatement = connection.prepareStatement(datosLaboralesQuery)) {
                    datosLaboralesStatement.setString(1, datosLaborales.getCargo());
                    datosLaboralesStatement.setBigDecimal(2, datosLaborales.getIngresoMensual());
                    datosLaboralesStatement.setString(3, datosLaborales.getNombreLugarTrabajo());
                    datosLaboralesStatement.setDate(4, new java.sql.Date(datosLaborales.getFechaIngresoTrabajo().getTime()));
                    datosLaboralesStatement.setString(5, solicitud.getCliente().getDni());
                    datosLaboralesStatement.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error al registrar datos laborales: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // Registrar las referencias personales, si se proporcionan
        List<ReferenciasPersonales> referenciasPersonalesList = solicitud.getReferenciasPersonales();
        if (referenciasPersonalesList != null) {
            for (ReferenciasPersonales referencia : referenciasPersonalesList) {
                String referenciasQuery = "INSERT INTO central.referenciaspersonales (primernombre, primerapellido, telresi, telcel, relacionSolicitante, idsolicitud) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement referenciasStatement = connection.prepareStatement(referenciasQuery)) {
                    referenciasStatement.setString(1, referencia.getPrimerNombre());
                    referenciasStatement.setString(2, referencia.getPrimerApellido());
                    referenciasStatement.setString(3, referencia.getTelResi());
                    referenciasStatement.setString(4, referencia.getTelCel());
                    referenciasStatement.setString(5, referencia.getRelacionSolicitante());
                    referenciasStatement.setLong(6, solicitud.getIdSolicitud());
                    referenciasStatement.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error al registrar referencias personales: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
        e.printStackTrace();
    }
}

@Override
public List<Solicitud> getAllSolicitudes(String username, String password) throws SQLException {
    List<Solicitud> solicitudes = new ArrayList<>();

    try (Connection connection = databaseConnection.getConnection(username, password)) {
        String query = "SELECT idsolicitud FROM central.solicitud";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Solicitud solicitud = new Solicitud();
                // Asume que tienes setters en la clase Solicitud para asignar los valores
                solicitud.setIdSolicitud(resultSet.getLong("idsolicitud"));
                // Setea el resto de los campos...
                //solicitud.setDni(resultSet.getString("dni"));
                // También debes recuperar las relaciones como empleado, cliente, etc.

                solicitudes.add(solicitud);
            }
        }
    }

    return solicitudes;
}

@Override
public Solicitud getSolicitudById(String username, String password, long idSolicitud) throws SQLException {
    Solicitud solicitud = null;

    try (Connection connection = databaseConnection.getConnection(username, password)) {
        String query = "SELECT s.idsolicitud, s.dni, s.idempleado, s.idtipoproducto, s.idestadosoli, " +
                       "c.primernombre AS cliente_primernombre, c.segundonombre AS cliente_segundonombre, " +
                       "c.primerapellido AS cliente_primerapellido, c.segundoapellido AS cliente_segundoapellido, " +
                       "c.correo AS cliente_correo, e.primernombre AS empleado_primernombre, " +
                       "e.primerapellido AS empleado_primerapellido, tp.nombreproducto AS tipo_producto_nombre, " +
                       "tp.limitecredito AS tipo_producto_limitcredito, tp.idmarca AS tipo_producto_idmarca, " +
                       "m.nombremarca AS marca_nombre, m.idmarca as marca_idmarca, es.descripestadosoli AS estado_descripcion, " +
                       "r.idrefpersonales, r.primernombre AS ref_primernombre, r.primerapellido AS ref_primerapellido, " +
                       "r.telresi AS ref_telresi, r.telcel AS ref_telcel, r.relacionsolicitante AS ref_relacion, " +
                       "dl.cargo AS datos_laborales_cargo, dl.iddatoslaborales AS id_datos_laborales, dl.ingresomensual AS datos_laborales_ingresomensual, " +
                       "dl.nombrelugartrabajo AS datos_laborales_lugartrabajo, dl.fechaingresotrabajo AS datos_laborales_fechaingreso " +
                       "FROM central.solicitud s " +
                       "JOIN central.cliente c ON s.dni = c.dni " +
                       "JOIN central.empleado e ON s.idempleado = e.idempleado " +
                       "JOIN central.tipoproducto tp ON s.idtipoproducto = tp.idtipoproducto " +
                       "LEFT JOIN central.marca m ON tp.idmarca = m.idmarca " +
                       "JOIN central.estadosolicitud es ON s.idestadosoli = es.idestadosoli " +
                       "LEFT JOIN central.referenciaspersonales r ON s.idsolicitud = r.idsolicitud " +
                       "LEFT JOIN central.datoslaborales dl ON c.dni = dl.dni " +
                       "WHERE s.idsolicitud = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, idSolicitud);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    solicitud = new Solicitud();
                    solicitud.setIdSolicitud(resultSet.getLong("idsolicitud"));

                    // Mapeo del cliente
                    Cliente cliente = new Cliente();
                    cliente.setDni(resultSet.getString("dni"));
                    cliente.setPrimerNombre(resultSet.getString("cliente_primernombre"));
                    cliente.setSegundoNombre(resultSet.getString("cliente_segundonombre"));
                    cliente.setPrimerApellido(resultSet.getString("cliente_primerapellido"));
                    cliente.setSegundoApellido(resultSet.getString("cliente_segundoapellido"));
                    cliente.setCorreo(resultSet.getString("cliente_correo"));

                    // Mapeo de los datos laborales
                    List<DatosLaborales> datosLaboralesList = new ArrayList<>();
                    DatosLaborales datosLaborales = new DatosLaborales();
                    datosLaborales.setIdDatosLaborales(resultSet.getLong("id_datos_laborales"));
                    datosLaborales.setCargo(resultSet.getString("datos_laborales_cargo"));
                    datosLaborales.setIngresoMensual(resultSet.getBigDecimal("datos_laborales_ingresomensual"));
                    datosLaborales.setNombreLugarTrabajo(resultSet.getString("datos_laborales_lugartrabajo"));
                    datosLaborales.setFechaIngresoTrabajo(resultSet.getDate("datos_laborales_fechaingreso"));
                    datosLaborales.setCliente(cliente);
                    datosLaboralesList.add(datosLaborales);
                    cliente.setDatosLaborales(datosLaboralesList);
                    
                    solicitud.setCliente(cliente);

                    // Mapeo del empleado
                    Empleado empleado = new Empleado();
                    empleado.setIdEmpleado(resultSet.getString("idempleado"));
                    empleado.setPrimerNombre(resultSet.getString("empleado_primernombre"));
                    empleado.setPrimerApellido(resultSet.getString("empleado_primerapellido"));
                    solicitud.setEmpleado(empleado);

                    // Mapeo del tipo de producto
                    TipoProducto tipoProducto = new TipoProducto();
                    tipoProducto.setIdTipoProducto(resultSet.getLong("idtipoproducto"));
                    tipoProducto.setNombreProducto(resultSet.getString("tipo_producto_nombre"));
                    tipoProducto.setLimiteCredito(resultSet.getBigDecimal("tipo_producto_limitcredito"));
          
                    // Mapeo de la marca
                    Marca marca = new Marca();  //marca_idmarca
                    marca.setIdMarca(resultSet.getLong("marca_idmarca"));
                    marca.setNombreMarca(resultSet.getString("marca_nombre"));
                    tipoProducto.setMarca(marca);

                    solicitud.setTipoProducto(tipoProducto);

                    // Mapeo del estado de la solicitud
                    EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
                    estadoSolicitud.setIdEstadoSoli(resultSet.getLong("idestadosoli"));
                    estadoSolicitud.setDescripEstadoSoli(resultSet.getString("estado_descripcion"));
                    solicitud.setEstadoSolicitud(estadoSolicitud);

                    // Mapeo de las referencias personales
                    Set<ReferenciasPersonales> referenciasPersonales = new HashSet<>();
                    do {
                        ReferenciasPersonales referenciaPersonal = new ReferenciasPersonales();
                        referenciaPersonal.setIdRefPersonales(resultSet.getLong("idrefpersonales"));
                        referenciaPersonal.setPrimerNombre(resultSet.getString("ref_primernombre"));
                        referenciaPersonal.setPrimerApellido(resultSet.getString("ref_primerapellido"));
                        referenciaPersonal.setTelResi(resultSet.getString("ref_telresi"));
                        referenciaPersonal.setTelCel(resultSet.getString("ref_telcel"));
                        referenciaPersonal.setRelacionSolicitante(resultSet.getString("ref_relacion"));
                        referenciasPersonales.add(referenciaPersonal);
                    } while (resultSet.next());

                    solicitud.setReferenciasPersonales(new ArrayList<>(referenciasPersonales));
                }
            }
        }
    }

    return solicitud;
}

@Override
public void denegarSolicitud(String username, String password, long idSolicitud) throws SQLException {
    String updateQuery = "UPDATE central.solicitud SET idestadosoli = ? WHERE idsolicitud = ?";

    try (Connection connection = databaseConnection.getConnection(username, password);
         PreparedStatement statement = connection.prepareStatement(updateQuery)) {

        // Establecer el estado "denegado" con el valor 1
        statement.setLong(1, 1);
        statement.setLong(2, idSolicitud);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated == 0) {
            throw new SQLException("No se encontró la solicitud con el ID especificado.");
        }
    }

    
}

@Override
public void aceptarSolicitud(String username, String password, long idSolicitud) throws SQLException {
    String updateSolicitudQuery = "UPDATE central.solicitud SET idestadosoli = ? WHERE idsolicitud = ?";
    String insertTarjetaQuery = "INSERT INTO central.tarjeta " +
                                "(idtarjeta, fechaapertura, fechavencimiento, fechacorte, pin, saldoactual, saldodisponible, pagominimo, fechalimitepago, tasainteresanual, idtipoproducto, dni, idestadotarjeta) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = databaseConnection.getConnection(username, password);
         PreparedStatement updateStatement = connection.prepareStatement(updateSolicitudQuery);
         PreparedStatement insertStatement = connection.prepareStatement(insertTarjetaQuery)) {

        // Actualiza el estado de la solicitud a "aceptada" (valor 2)
        updateStatement.setLong(1, 2);
        updateStatement.setLong(2, idSolicitud);

        int rowsUpdated = updateStatement.executeUpdate();
        if (rowsUpdated == 0) {
            throw new SQLException("No se encontró la solicitud con el ID especificado.");
        }

        // Recupera la información necesaria para la tarjeta
        String selectSolicitudQuery = "SELECT dni, idtipoproducto FROM central.solicitud WHERE idsolicitud = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSolicitudQuery)) {
            selectStatement.setLong(1, idSolicitud);
            ResultSet resultSet = selectStatement.executeQuery();
            
            if (resultSet.next()) {
                String dni = resultSet.getString("dni");
                long idTipoProducto = resultSet.getLong("idtipoproducto");

                // Obtiene los datos del tipo de producto
                String selectTipoProductoQuery = "SELECT limitecredito FROM central.tipoproducto WHERE idtipoproducto = ?";
                try (PreparedStatement tipoProductoStatement = connection.prepareStatement(selectTipoProductoQuery)) {
                    tipoProductoStatement.setLong(1, idTipoProducto);
                    ResultSet tipoProductoResultSet = tipoProductoStatement.executeQuery();

                    if (tipoProductoResultSet.next()) {
                        BigDecimal limiteCredito = tipoProductoResultSet.getBigDecimal("limitecredito");
                        BigDecimal saldoDisponible = limiteCredito;
                        BigDecimal pagoMinimo = limiteCredito.multiply(BigDecimal.valueOf(0.25)); // 25% del límite de crédito

                        // Genera un ID de tarjeta único
                        long idTarjeta = generateUniqueCardId();

                        // Configura la inserción de la nueva tarjeta
                        insertStatement.setLong(1, idTarjeta);
                        insertStatement.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Fecha de apertura
                        insertStatement.setDate(3, calculateExpirationDate()); // Fecha de vencimiento (puede ser calculada)
                        insertStatement.setDate(4, calculateCutOffDate()); // Fecha de corte (puede ser calculada)
                        insertStatement.setInt(5, generatePin()); // PIN
                        insertStatement.setBigDecimal(6, BigDecimal.ZERO);
                        insertStatement.setBigDecimal(7, saldoDisponible);
                        insertStatement.setBigDecimal(8, pagoMinimo);
                        insertStatement.setDate(9, calculatePaymentDueDate()); // Fecha límite de pago (puede ser calculada)
                        insertStatement.setBigDecimal(10, BigDecimal.valueOf(15.0)); // Tasa de interés anual (puede ser calculada o fija)
                        insertStatement.setLong(11, idTipoProducto);
                        insertStatement.setString(12, dni);
                        insertStatement.setLong(13, 1); // Estado de la tarjeta (puede ser un valor predeterminado)

                        insertStatement.executeUpdate();
                    } else {
                        throw new SQLException("Tipo de producto no encontrado.");
                    }
                }
            } else {
                throw new SQLException("Solicitud no encontrada.");
            }
        }
    }
}

private long generateUniqueCardId() {
    // Genera un ID único de 16 dígitos
    // El número estara en el rango de 16 dígitos (1000000000000000L a 9999999999999999L)
    long min = 1000000000000000L;
    long max = 9999999999999999L;
    return min + (long) (Math.random() * (max - min));
}

private java.sql.Date calculateExpirationDate() {
    // Implementa la lógica para calcular la fecha de vencimiento
    return new java.sql.Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000); // Ejemplo simplificado
}

private java.sql.Date calculateCutOffDate() {
    // Implementa la lógica para calcular la fecha de corte
    return new java.sql.Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000); // Ejemplo simplificado
}

private int generatePin() {
    // Implementa la lógica para generar un PIN seguro
    return new Random().nextInt(10000); // Ejemplo simplificado
}

private java.sql.Date calculatePaymentDueDate() {
    // Implementa la lógica para calcular la fecha límite de pago
    return new java.sql.Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000); // Ejemplo simplificado
}
 
@Override
public List<Solicitud> obtenerSolicitudesCliente(String username, String password) throws SQLException {
    String queryDniCliente = "SELECT dni FROM central.cliente WHERE usuarioc = ?";
    String querySolicitudes = "SELECT s.idsolicitud, s.dni, s.idtipoproducto, s.idestadosoli, es.descripestadosoli " +
                              "FROM central.solicitud s " +
                              "LEFT JOIN central.estadosolicitud es ON s.idestadosoli = es.idestadosoli " +
                              "WHERE s.dni = ?";

    List<Solicitud> solicitudes = new ArrayList<>();

    try (Connection connection = databaseConnection.getConnection(username, password)) {
        String dniCliente = null;

        // Obtener el DNI del cliente basado en el usuario
        try (PreparedStatement statementDni = connection.prepareStatement(queryDniCliente)) {
            statementDni.setString(1, username);
            try (ResultSet resultSetDni = statementDni.executeQuery()) {
                if (resultSetDni.next()) {
                    dniCliente = resultSetDni.getString("dni");
                } else {
                    throw new SQLException("Cliente no encontrado");
                }
            }
        }

        // Obtener las solicitudes del cliente
        try (PreparedStatement statementSolicitudes = connection.prepareStatement(querySolicitudes)) {
            statementSolicitudes.setString(1, dniCliente);
            try (ResultSet resultSetSolicitudes = statementSolicitudes.executeQuery()) {
                while (resultSetSolicitudes.next()) {
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(resultSetSolicitudes.getLong("idsolicitud"));

                    // Crear y asignar el EstadoSolicitud a la solicitud
                    EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
                    estadoSolicitud.setIdEstadoSoli(resultSetSolicitudes.getLong("idestadosoli"));
                    estadoSolicitud.setDescripEstadoSoli(resultSetSolicitudes.getString("descripestadosoli"));
                    solicitud.setEstadoSolicitud(estadoSolicitud);

                    solicitudes.add(solicitud);
                }
            }
        }

        return solicitudes;
    }
}


}
