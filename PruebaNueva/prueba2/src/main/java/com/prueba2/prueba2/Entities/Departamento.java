//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "departamento")
@Data
public class Departamento {

    @Id
    @Column(name = "iddepto")
    private Long idDepto;

    @Column(name = "nombredepto", length = 50)
    private String nombreDepto;

    @OneToMany(mappedBy = "departamento")
    private List<Ciudad> ciudades;

}
