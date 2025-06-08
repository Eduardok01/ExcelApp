package com.ufro.Ficha_viajes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaIda;
    private LocalDate fechaVuelta;

    @Embedded
    private Conductor conductor;

    @Embedded
    private Camion camion;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private List<Carga> cargasIda;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "viaje_id")
    private List<Carga> cargasVuelta;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "litros", column = @Column(name = "litros_petroleo_ida")),
            @AttributeOverride(name = "precioLitro", column = @Column(name = "precio_litro_petroleo_ida")),
            @AttributeOverride(name = "total", column = @Column(name = "total_petroleo_ida"))
    })
    private Petroleo petroleoIda;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "litros", column = @Column(name = "litros_petroleo_vuelta")),
            @AttributeOverride(name = "precioLitro", column = @Column(name = "precio_litro_petroleo_vuelta")),
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

    // Campos financieros
    private Double ingresosTotales;   // Suma de ingresos de cargas ida + vuelta
    private Double gastosTotales;     // Suma de todos los gastos (viáticos, peajes, petroleo, adBlue, neumático)
    private Double gananciaTotal;     // ingresosTotales - gastosTotales

    // Nuevo método para calcular total de viáticos
    public double getTotalViaticos() {
        if (viaticos == null || viaticos.isEmpty()) {
            return 0;
        }
        return viaticos.stream().mapToDouble(Viatico::getMonto).sum();
    }

    // Método para calcular total de peajes usando el campo 'costo'
    public double getTotalPeajes() {
        if (peajes == null || peajes.isEmpty()) {
            return 0;
        }
        return peajes.stream().mapToDouble(Peaje::getCosto).sum();
    }

    // Getters y setters para gastosTotales (si usas Lombok @Data, no es obligatorio, pero para claridad)

    public Double getGastosTotales() {
        return gastosTotales;
    }

    public void setGastosTotales(Double gastosTotales) {
        this.gastosTotales = gastosTotales;
    }
}
