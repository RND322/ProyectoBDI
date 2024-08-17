//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "datoslaborales")
@Data
public class DatosLaborales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddatoslaborales")
    private Long idDatosLaborales;

    @Column(name = "cargo", length = 50)
    private String cargo;

    @Column(name = "ingresomensual", precision = 15, scale = 2)
    private BigDecimal ingresoMensual;

    @Column(name = "nombrelugartrabajo", length = 100)
    private String nombreLugarTrabajo;

    @Column(name = "fechaingresotrabajo")
    private Date fechaIngresoTrabajo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "dni")
    private Cliente cliente;

}
