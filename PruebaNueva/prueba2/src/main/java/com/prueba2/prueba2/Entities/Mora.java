//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mora")
@Data
public class Mora {

    @Id
    @Column(name = "idmora")
    private Long idMora;

    @Column(name = "saldovencido", precision = 15, scale = 2)
    private BigDecimal saldoVencido;

    @Column(name = "tasainteresmora", precision = 3, scale = 2)
    private BigDecimal tasaInteresMora;

    @ManyToOne
    @JoinColumn(name = "idtarjeta")
    private Tarjeta tarjeta;

}
