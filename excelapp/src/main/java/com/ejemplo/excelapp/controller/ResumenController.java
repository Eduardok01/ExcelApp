package com.ejemplo.excelapp.controller;

import com.ejemplo.excelapp.model.Resumen;
import com.ejemplo.excelapp.repository.ResumenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumenes")
public class ResumenController {

    private final ResumenRepository resumenRepository;

    public ResumenController(ResumenRepository resumenRepository) {
        this.resumenRepository = resumenRepository;
    }

    // Guardar un resumen (simulando el formulario)
    @PostMapping
    public Resumen crearResumen(@RequestBody Resumen resumen) {
        return resumenRepository.save(resumen);
    }

    // Obtener todos los resumenes (lista)
    @GetMapping
    public List<Resumen> obtenerResumenes() {
        return resumenRepository.findAll();
    }
}
