//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "solicitud")
@Data
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsolicitud", length = 10)
    private Long idSolicitud;

    @ManyToOne
    @JoinColumn(name = "dni")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idempleado")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "idtipoproducto")
    private TipoProducto tipoProducto;

    @ManyToOne
    @JoinColumn(name = "idestadosoli")
    private EstadoSolicitud estadoSolicitud;

}
