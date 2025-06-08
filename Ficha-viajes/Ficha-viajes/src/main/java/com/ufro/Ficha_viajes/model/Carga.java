package com.ufro.Ficha_viajes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Carga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoCarga;

    private Double kilos;

    // Precio por kilo (valor unitario)
    private Double precioPorKilo;

    // Total de la carga (kilos * precioPorKilo)
    private Double totalCarga;

    // Se calcula automáticamente antes de guardar o actualizar
    @PrePersist
    @PreUpdate
    public void calcularTotalCarga() {
        if (kilos != null && precioPorKilo != null) {
            this.totalCarga = kilos * precioPorKilo;
        } else {
            this.totalCarga = 0.0;
        }
    }
}
