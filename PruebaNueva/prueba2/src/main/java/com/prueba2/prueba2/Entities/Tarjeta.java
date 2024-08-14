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
@Table(name = "tarjeta")
@Data
public class Tarjeta {

    @Id
    @Column(name = "idtarjeta")
    private Long idTarjeta;

    @Column(name = "fechaapertura")
    private Date fechaApertura;

    @Column(name = "fechavencimiento")
    private Date fechaVencimiento;

    @Column(name = "fechacorte")
    private Date fechaCorte;

    @Column(name = "pin")
    private Integer pin;

    @Column(name = "saldoactual", precision = 15, scale = 2)
    private BigDecimal saldoActual;

    @Column(name = "saldodisponible", precision = 15, scale = 2)
    private BigDecimal saldoDisponible;

    @Column(name = "pagominimo", precision = 15, scale = 2)
    private BigDecimal pagoMinimo;

    @Column(name = "fechalimitepago")
    private Date fechaLimitePago;

    @Column(name = "tasainteresanual", precision = 5, scale = 2)
    private BigDecimal tasaInteresAnual;

    @ManyToOne
    @JoinColumn(name = "idtipoproducto")
    private TipoProducto tipoProducto;

    @ManyToOne
    @JoinColumn(name = "dni")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idestadotarjeta")
    private EstadoTarjeta estadoTarjeta;

}