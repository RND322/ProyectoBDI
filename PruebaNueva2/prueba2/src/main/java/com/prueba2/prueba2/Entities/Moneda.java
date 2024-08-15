//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "moneda")
@Data
public class Moneda {

    @Id
    @Column(name = "idmoneda")
    private Long idMoneda;

    @Column(name = "nombremoneda", length = 50)
    private String nombreMoneda;

}
