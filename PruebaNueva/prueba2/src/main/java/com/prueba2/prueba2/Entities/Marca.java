//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "marca")
@Data
public class Marca {

    @Id
    @Column(name = "idmarca")
    private Long idMarca;

    @Column(name = "nombremarca", length = 50)
    private String nombreMarca;

}
