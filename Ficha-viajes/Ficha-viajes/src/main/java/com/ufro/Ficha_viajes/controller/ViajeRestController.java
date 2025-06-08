package com.ufro.Ficha_viajes.controller;

import com.ufro.Ficha_viajes.model.Viaje;
import com.ufro.Ficha_viajes.service.PdfService;
import com.ufro.Ficha_viajes.service.ViajeService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/viajes")
@CrossOrigin(origins = "http://localhost:8080") // Cambia al dominio o puerto de tu frontend Vue
public class ViajeRestController {

    private final ViajeService viajeService;
    private final PdfService pdfService;

    public ViajeRestController(ViajeService viajeService, PdfService pdfService) {
        this.viajeService = viajeService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public ResponseEntity<List<Viaje>> listarViajes() {
        List<Viaje> viajes = viajeService.getAll();
        return ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> obtenerViaje(@PathVariable Long id) {
        return viajeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> descargarPdf(@PathVariable Long id) {
        return viajeService.getById(id).map(viaje -> {
            ByteArrayInputStream pdf = pdfService.generarFichaViajePdf(viaje);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ficha-viaje-" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdf));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Viaje> crearViaje(@RequestBody Viaje viaje) {
        Viaje guardado = viajeService.save(viaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
