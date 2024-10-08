//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estadocivil")
@Data
public class EstadoCivil {

    @Id
    @Column(name = "idestadocivil")
    private Long idEstadoCivil;

    @Column(name = "descripestadocivil", length = 15)
    private String descripEstadoCivil;
    
}
