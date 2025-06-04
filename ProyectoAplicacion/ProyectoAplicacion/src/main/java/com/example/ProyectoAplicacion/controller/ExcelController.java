package com.example.ProyectoAplicacion.controller;

import com.example.ProyectoAplicacion.service.ViajeExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/viajes")
public class ExcelController {

    @Autowired
    private ViajeExcelService viajeExcelService;

    @PostMapping("/cargar")
    public ResponseEntity<String> importarViajes(@RequestParam("archivo") MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío");
        }

        try {
            viajeExcelService.guardarDesdeExcel(archivo);
            return ResponseEntity.ok("Archivo importado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al procesar el archivo: " + e.getMessage());
        }
    }
}


