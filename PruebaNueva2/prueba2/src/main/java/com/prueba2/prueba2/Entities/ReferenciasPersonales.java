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
@Table(name = "referenciaspersonales")
@Data
public class ReferenciasPersonales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrefpersonales")
    private Long idRefPersonales;

    @Column(name = "primernombre", length = 50)
    private String primerNombre;

    @Column(name = "primerapellido", length = 50)
    private String primerApellido;

    @Column(name = "telresi", length = 15)
    private String telResi;

    @Column(name = "telcel", length = 15)
    private String telCel;

    @Column(name = "relacionsolicitante", length = 30)
    private String relacionSolicitante;

    @ManyToOne
    @JoinColumn(name = "idsolicitud")
    private Solicitud solicitud;

}