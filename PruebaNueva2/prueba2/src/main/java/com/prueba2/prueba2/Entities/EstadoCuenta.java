//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estadocuenta")
@Data
public class EstadoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestadocuenta")
    private Long idEstadoCuenta;

    @Column(name = "saldoanterior", precision = 15, scale = 2)
    private BigDecimal saldoAnterior;

    @Column(name = "compras", precision = 15, scale = 2)
    private BigDecimal compras;

    @Column(name = "abonos", precision = 15, scale = 2)
    private BigDecimal abonos;

    @Column(name = "interesesadeudados", precision = 15, scale = 2)
    private BigDecimal interesesAdeudados;

    @Column(name = "estadomora", length = 1)
    private Integer estadoMora;

    @ManyToOne
    @JoinColumn(name = "idtarjeta")
    private Tarjeta tarjeta;

}
