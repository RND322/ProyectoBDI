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
@Table(name = "tipoproducto")
@Data
public class TipoProducto {

    @Id
    @Column(name = "idtipoproducto")
    private Long idTipoProducto;

    @Column(name = "nombreproducto", length = 50)
    private String nombreProducto;

    @Column(name = "limitecredito", precision = 10, scale = 2)
    private BigDecimal limiteCredito;

    @ManyToOne
    @JoinColumn(name = "idmarca")
    private Marca marca;

}
