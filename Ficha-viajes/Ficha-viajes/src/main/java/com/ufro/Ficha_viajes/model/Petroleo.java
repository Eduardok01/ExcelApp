package com.ufro.Ficha_viajes.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Petroleo {
    private Double litros;
    private Double precioLitro;
    private Double total;
}
