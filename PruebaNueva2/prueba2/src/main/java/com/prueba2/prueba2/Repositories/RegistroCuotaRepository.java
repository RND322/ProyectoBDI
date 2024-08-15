//Agregada el 13/8/24
package com.prueba2.prueba2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba2.prueba2.Entities.RegistroCuota;

@Repository
public interface RegistroCuotaRepository extends JpaRepository<RegistroCuota, Long> {
    
}

