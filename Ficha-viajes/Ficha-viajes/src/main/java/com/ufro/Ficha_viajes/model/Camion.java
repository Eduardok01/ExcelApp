package com.ufro.Ficha_viajes.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Camion {
    private String patente;
}
