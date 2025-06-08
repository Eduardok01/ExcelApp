package com.ufro.Ficha_viajes.controller;

import org.springframework.ui.Model;
import com.ufro.Ficha_viajes.service.ViajeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/viajes")
public class ViajeViewController {

    private final ViajeService viajeService;

    public ViajeViewController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping("/{id}")
    public String mostrarFichaViaje(@PathVariable Long id, Model model) {
        return viajeService.getById(id)
                .map(viaje -> {
                    model.addAttribute("viaje", viaje);
                    return "ficha-viaje"; // Thymeleaf template
                })
                .orElse("error/404"); // Página de error personalizada
    }
}
