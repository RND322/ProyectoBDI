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
@Table(name = "registrocuota")
@Data
public class RegistroCuota {

    @Id
    @Column(name = "idregistrocuota")
    private Long idRegistroCuota;

    @ManyToOne
    @JoinColumn(name = "idmora")
    private Mora mora;

    @ManyToOne
    @JoinColumn(name = "idtarjeta")
    private Tarjeta tarjeta;

    @Column(name = "numerocuota")
    private Integer numeroCuota;

}