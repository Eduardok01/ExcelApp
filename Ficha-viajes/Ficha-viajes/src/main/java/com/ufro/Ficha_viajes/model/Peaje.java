package com.ufro.Ficha_viajes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Peaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Nombre o lugar del peaje

    private Double costo;  // Costo del peaje

    // Opcional: fecha o momento en que se pagó el peaje (puede ser útil)
    // @Column(name = "fecha_peaje")
    // private LocalDate fechaPeaje;
}
