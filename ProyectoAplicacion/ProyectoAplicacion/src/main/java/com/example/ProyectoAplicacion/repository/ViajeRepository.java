package com.example.ProyectoAplicacion.repository;

import com.example.ProyectoAplicacion.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    // Búsqueda por nombre del conductor
    List<Viaje> findByNombreConductor(String nombreConductor);

    // Búsqueda por patente
    List<Viaje> findByPatente(String patente);

    // Búsqueda por RUT del conductor
    List<Viaje> findByRutConductor(String rutConductor);
}
