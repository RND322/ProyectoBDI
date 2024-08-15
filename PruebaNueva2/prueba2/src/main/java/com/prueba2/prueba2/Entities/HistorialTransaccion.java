//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

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
@Table(name = "historialtransaccion")
@Data
public class HistorialTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhistorialtransaccion")
    private Long idHistorialTransaccion;

    @ManyToOne
    @JoinColumn(name = "idtarjeta")
    private Tarjeta tarjeta;

    @ManyToOne
    @JoinColumn(name = "idtransac")
    private Transaccion transaccion;

}

