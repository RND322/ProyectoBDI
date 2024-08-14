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
@Table(name = "telefono")
@Data
public class Telefono {

    @Id
    @Column(name = "idtelefono", length = 15)
    private String idTelefono;

    @Column(name = "telefonohogar", length = 15)
    private String telefonoHogar;

    @Column(name = "telefonocelular", length = 15)
    private String telefonoCelular;

    @ManyToOne
    @JoinColumn(name = "dni")
    private Cliente cliente;

}