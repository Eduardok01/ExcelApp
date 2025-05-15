package com.ejemplo.excelapp.controller;

import com.ejemplo.excelapp.model.ViajeResumen;
import com.ejemplo.excelapp.repository.ViajeResumenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viajes")
public class ViajeResumenController {

    private final ViajeResumenRepository viajeResumenRepository;

    public ViajeResumenController(ViajeResumenRepository viajeResumenRepository) {
        this.viajeResumenRepository = viajeResumenRepository;
    }

    // Crear un nuevo viaje
    @PostMapping
    public ViajeResumen crearViaje(@RequestBody ViajeResumen viajeResumen) {
        return viajeResumenRepository.save(viajeResumen);
    }

    // Obtener todos los viajes
    @GetMapping
    public List<ViajeResumen> obtenerViajes() {
        return viajeResumenRepository.findAll();
    }
}
