package com.ufro.Ficha_viajes.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AdBlue {
    private boolean uso;      // true si se usó AdBlue (tipo primitivo boolean)
    private Double costo;     // costo asociado al uso de AdBlue
}
