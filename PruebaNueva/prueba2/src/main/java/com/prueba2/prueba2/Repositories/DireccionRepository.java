//Agregada el 13/8/24
package com.prueba2.prueba2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba2.prueba2.Entities.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    
}
