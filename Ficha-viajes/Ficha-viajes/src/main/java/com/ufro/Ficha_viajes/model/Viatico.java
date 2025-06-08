package com.ufro.Ficha_viajes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Viatico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;
    private String descripcion;

    // Puedes agregar un campo de fecha si quieres registrar cuándo se entregó el viático
    // private LocalDate fecha;
}
