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
@Table(name = "ciudad")
@Data
public class Ciudad {

    @Id
    @Column(name = "idciudad")
    private Long idCiudad;

    @Column(name = "nombreciudad", length = 50)
    private String nombreCiudad;

    @ManyToOne
    @JoinColumn(name = "iddepto")
    private Departamento departamento;
    
}