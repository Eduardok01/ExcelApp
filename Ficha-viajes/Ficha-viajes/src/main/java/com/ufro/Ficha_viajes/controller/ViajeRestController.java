package com.ufro.Ficha_viajes.controller;

import com.ufro.Ficha_viajes.model.Viaje;
import com.ufro.Ficha_viajes.service.PdfService;
import com.ufro.Ficha_viajes.service.ViajeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/viajes")
@CrossOrigin(origins = "http://localhost:5173")
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

    // Endpoint para descargar el PDF generado
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
        return viajeService.getById(id).map(viaje -> {

            // Calcular resumen financiero antes de generar PDF
            double ingresos = viajeService.calcularIngresosTotales(viaje);
            double gastos = viajeService.calcularGastosTotales(viaje);
            double ganancia = ingresos - gastos;

            viaje.setIngresosTotales(ingresos);
            viaje.setGastosTotales(gastos);
            viaje.setGananciaTotal(ganancia);

            ByteArrayInputStream pdfStream = pdfService.generarFichaViajePdf(viaje);
            byte[] pdfBytes;
            try {
                pdfBytes = pdfStream.readAllBytes();
            } catch (Exception e) {
                throw new RuntimeException("Error leyendo bytes del PDF", e);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ficha-viaje-" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para crear un nuevo viaje
    @PostMapping
    public ResponseEntity<Viaje> crearViaje(@RequestBody Viaje viaje) {
        Viaje guardado = viajeService.save(viaje);
        return ResponseEntity.status(201).body(guardado);
    }

    // Endpoint para generar PDF a partir de un viaje enviado en el body (sin guardar)
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generarPdfDesdeFicha(@RequestBody Viaje viaje) {
        // Calcular resumen financiero antes de generar PDF
        double ingresos = viajeService.calcularIngresosTotales(viaje);
        double gastos = viajeService.calcularGastosTotales(viaje);
        double ganancia = ingresos - gastos;

        viaje.setIngresosTotales(ingresos);
        viaje.setGastosTotales(gastos);
        viaje.setGananciaTotal(ganancia);

        ByteArrayInputStream pdfStream = pdfService.generarFichaViajePdf(viaje);
        byte[] pdfBytes;
        try {
            pdfBytes = pdfStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo bytes del PDF", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ficha-viaje.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    // Endpoint para generar y guardar HTML para inspección (opcional)
    @GetMapping("/{id}/html")
    public ResponseEntity<String> generarHtml(@PathVariable Long id) {
        return viajeService.getById(id).map(viaje -> {
            pdfService.guardarHtmlGenerado(viaje, "ficha-viaje-" + id + ".html");
            return ResponseEntity.ok("HTML guardado para inspección.");
        }).orElse(ResponseEntity.notFound().build());
    }
}
