//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estadosolicitud")
@Data
public class EstadoSolicitud {

    @Id
    @Column(name = "idestadosoli")
    private Long idEstadoSoli;

    @Column(name = "descripestadosoli", length = 50)
    private String descripEstadoSoli;

}
