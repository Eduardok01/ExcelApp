package com.ufro.Ficha_viajes.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Neumatico {
    private boolean cambio;           // true si hubo cambio de neumático (tipo primitivo boolean)
    private String detalleCambio;     // detalle o cuál neumático se cambió
    private Double costo;             // costo del cambio
}
