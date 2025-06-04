package com.example.ProyectoAplicacion.service;

import com.example.ProyectoAplicacion.model.Viaje;
import com.example.ProyectoAplicacion.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;

    public ViajeService(ViajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
    }

    public Viaje guardarViaje(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    public List<Viaje> buscarPorNombreConductor(String nombreConductor) {
        return viajeRepository.findByNombreConductor(nombreConductor);
    }

    // Métodos extra que puedes usar para búsqueda avanzada
    public List<Viaje> buscarPorPatente(String patente) {
        return viajeRepository.findByPatente(patente);
    }

    public List<Viaje> buscarPorRutConductor(String rutConductor) {
        return viajeRepository.findByRutConductor(rutConductor);
    }
}
