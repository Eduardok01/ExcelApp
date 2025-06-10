package com.ufro.Ficha_viajes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de ida es obligatoria")
    private LocalDate fechaIda;

    @NotNull(message = "La fecha de vuelta es obligatoria")
    private LocalDate fechaVuelta;

    @Embedded
    @NotNull(message = "El conductor es obligatorio")
    private Conductor conductor;

    @Embedded
    @NotNull(message = "El camión es obligatorio")
    private Camion camion;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private List<Carga> cargasIda;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private List<Carga> cargasVuelta;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "litros", column = @Column(name = "litros_ida")),
            @AttributeOverride(name = "precioLitro", column = @Column(name = "precio_litro_ida")),
            @AttributeOverride(name = "total", column = @Column(name = "total_petroleo_ida"))
    })
    private Petroleo petroleoIda;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "litros", column = @Column(name = "litros_vuelta")),
            @AttributeOverride(name = "precioLitro", column = @Column(name = "precio_litro_vuelta")),
            @AttributeOverride(name = "total", column = @Column(name = "total_petroleo_vuelta"))
    })
    private Petroleo petroleoVuelta;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private List<Viatico> viaticos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private List<Peaje> peajes;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "uso", column = @Column(name = "adblue_uso")),
            @AttributeOverride(name = "costo", column = @Column(name = "adblue_costo"))
    })
    private AdBlue adBlue;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cambio", column = @Column(name = "neumatico_cambio")),
            @AttributeOverride(name = "detalleCambio", column = @Column(name = "neumatico_detalle_cambio")),
            @AttributeOverride(name = "costo", column = @Column(name = "neumatico_costo"))
    })
    private Neumatico neumatico;

    private Double ingresosTotales;
    private Double gastosTotales;
    private Double gananciaTotal;

    public double getTotalViaticos() {
        if (viaticos == null || viaticos.isEmpty()) {
            return 0;
        }
        return viaticos.stream().mapToDouble(Viatico::getMonto).sum();
    }

    public double getTotalPeajes() {
        if (peajes == null || peajes.isEmpty()) {
            return 0;
        }
        return peajes.stream().mapToDouble(Peaje::getCosto).sum();
    }

    public Double getGastosTotales() {
        return gastosTotales;
    }

    public void setGastosTotales(Double gastosTotales) {
        this.gastosTotales = gastosTotales;
    }

    public Double getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(Double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    public Double getGananciaTotal() {
        return gananciaTotal;
    }

    public void setGananciaTotal(Double gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
    }
}
