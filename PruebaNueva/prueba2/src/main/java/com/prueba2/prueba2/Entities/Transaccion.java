//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transaccion")
@Data
public class Transaccion {

    @Id
    @Column(name = "idtransac")
    private Long idTransac;

    @Column(name = "noreferencia", length = 15)
    private String noReferencia;

    @Column(name = "fechatransaccion")
    private Date fechaTransaccion;

    @Column(name = "concepto", length = 100)
    private String concepto;

    @Column(name = "monto", precision = 15, scale = 2)
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "idmoneda")
    private Moneda moneda;

}

