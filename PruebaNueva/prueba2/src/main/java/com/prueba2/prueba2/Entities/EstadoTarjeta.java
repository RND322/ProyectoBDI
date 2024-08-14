//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estadotarjeta")
@Data
public class EstadoTarjeta {

    @Id
    @Column(name = "idestadotarjeta")
    private Long idEstadoTarjeta;

    @Column(name = "tipoestado", length = 30)
    private String tipoEstado;

}
