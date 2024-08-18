//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

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
@Table(name = "direccion")
@Data
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private Long idDireccion;

    @ManyToOne
    @JoinColumn(name = "idciudad")
    private Ciudad ciudad;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "dni")
    private Cliente cliente;

}