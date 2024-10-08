//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sucursal")
@Data
public class Sucursal {

    @Id
    @Column(name = "idsucursal")
    private Long idSucursal;

    @Column(name = "nombresucursal", length = 100)
    private String nombreSucursal;

}