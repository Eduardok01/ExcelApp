package com.example.ProyectoAplicacion.controller;

import com.example.ProyectoAplicacion.model.Viaje;
import com.example.ProyectoAplicacion.service.ViajeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // este es el correcto
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ViajeController {

    private final ViajeService viajeService;

    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping("/viajes/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("viaje", new Viaje());
        return "fichaViaje";
    }

    @PostMapping("/viajes/guardar")
    public String guardarViaje(@ModelAttribute Viaje viaje, Model model) {
        viajeService.guardarViaje(viaje);
        model.addAttribute("mensajeExito", "Ficha guardada correctamente.");
        model.addAttribute("viaje", new Viaje());  // Limpiar formulario
        return "fichaViaje";
    }

    @GetMapping("/viajes/conductor/{nombre}")
    @ResponseBody
    public List<Viaje> buscarPorNombreConductor(@PathVariable String nombre) {
        return viajeService.buscarPorNombreConductor(nombre);
    }
}
