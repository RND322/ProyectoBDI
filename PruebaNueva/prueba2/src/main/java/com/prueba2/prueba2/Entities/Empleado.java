//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "empleado")
@Data
public class Empleado {

    @Id
    @Column(name = "idempleado", length = 15)
    private String idEmpleado;

    @Column(name = "usuarioe", length = 15)
    private String usuarioE;

    @Column(name = "primernombre", length = 50)
    private String primerNombre;

    @Column(name = "primerapellido", length = 50)
    private String primerApellido;

    @Column(name = "cargo", length = 50)
    private String cargo;

    @ManyToOne
    @JoinColumn(name = "idsucursal")
    private Sucursal sucursal;

}