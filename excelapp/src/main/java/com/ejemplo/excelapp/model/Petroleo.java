package com.ejemplo.excelapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Petroleo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal litros;

    public Petroleo() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }
}

