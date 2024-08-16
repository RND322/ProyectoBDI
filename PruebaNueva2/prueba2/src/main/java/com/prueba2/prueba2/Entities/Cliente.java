//Agregada el 13/8/24
package com.prueba2.prueba2.Entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id
    @Column(name = "dni", length = 15)
    private String dni;

    @Column(name = "usuarioc", length = 15)
    private String usuarioC;

    @Column(name = "primernombre", length = 50)
    private String primerNombre;

    @Column(name = "segundonombre", length = 50)
    private String segundoNombre;

    @Column(name = "primerapellido", length = 50)
    private String primerApellido;

    @Column(name = "segundoapellido", length = 50)
    private String segundoApellido;

    @Column(name = "correo", length = 100)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "idestadocivil")
    private EstadoCivil estadoCivil;

    @ManyToOne
    @JoinColumn(name = "idgenero")
    private Genero genero;

    @OneToMany(mappedBy = "cliente")
    private List<Tarjeta> tarjetas;

    @OneToMany(mappedBy = "cliente")
    private List<Direccion> direcciones;

    @OneToMany(mappedBy = "cliente")
    private List<Telefono> telefonos;

    @OneToMany(mappedBy = "cliente")
    private List<DatosLaborales> datosLaborales;

}
