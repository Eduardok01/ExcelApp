package com.example.ProyectoAplicacion.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreConductor;
    private String rutConductor;
    private LocalDate fechaViaje;
    private String patente;

    private String tipoCargaIda;
    private String tipoCargaVuelta;

    private BigDecimal fondoARendir;

    private BigDecimal viaticos;

    // Varios peajes como campos separados, puedes agregar más si quieres
    private BigDecimal peaje1;
    private BigDecimal peaje2;
    private BigDecimal peaje3;

    private BigDecimal litrosCombustible;
    private BigDecimal precioLitroCombustible;

    @Transient
    private BigDecimal gastoPetroleo;

    private Integer kmInicial;
    private Integer kmFinal;

    private Boolean cambioNeumatico;
    private String neumaticoCambiado;

    private Boolean usoAdBlue;

    private String otrosGastos;

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreConductor() { return nombreConductor; }
    public void setNombreConductor(String nombreConductor) { this.nombreConductor = nombreConductor; }

    public String getRutConductor() { return rutConductor; }
    public void setRutConductor(String rutConductor) { this.rutConductor = rutConductor; }

    public LocalDate getFechaViaje() { return fechaViaje; }
    public void setFechaViaje(LocalDate fechaViaje) { this.fechaViaje = fechaViaje; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getTipoCargaIda() { return tipoCargaIda; }
    public void setTipoCargaIda(String tipoCargaIda) { this.tipoCargaIda = tipoCargaIda; }

    public String getTipoCargaVuelta() { return tipoCargaVuelta; }
    public void setTipoCargaVuelta(String tipoCargaVuelta) { this.tipoCargaVuelta = tipoCargaVuelta; }

    public BigDecimal getFondoARendir() { return fondoARendir; }
    public void setFondoARendir(BigDecimal fondoARendir) { this.fondoARendir = fondoARendir; }

    public BigDecimal getViaticos() { return viaticos; }
    public void setViaticos(BigDecimal viaticos) { this.viaticos = viaticos; }

    public BigDecimal getPeaje1() { return peaje1; }
    public void setPeaje1(BigDecimal peaje1) { this.peaje1 = peaje1; }

    public BigDecimal getPeaje2() { return peaje2; }
    public void setPeaje2(BigDecimal peaje2) { this.peaje2 = peaje2; }

    public BigDecimal getPeaje3() { return peaje3; }
    public void setPeaje3(BigDecimal peaje3) { this.peaje3 = peaje3; }

    public BigDecimal getLitrosCombustible() { return litrosCombustible; }
    public void setLitrosCombustible(BigDecimal litrosCombustible) { this.litrosCombustible = litrosCombustible; }

    public BigDecimal getPrecioLitroCombustible() { return precioLitroCombustible; }
    public void setPrecioLitroCombustible(BigDecimal precioLitroCombustible) { this.precioLitroCombustible = precioLitroCombustible; }

    public BigDecimal getGastoPetroleo() {
        if (litrosCombustible != null && precioLitroCombustible != null) {
            return litrosCombustible.multiply(precioLitroCombustible);
        }
        return BigDecimal.ZERO;
    }

    public Integer getKmInicial() { return kmInicial; }
    public void setKmInicial(Integer kmInicial) { this.kmInicial = kmInicial; }

    public Integer getKmFinal() { return kmFinal; }
    public void setKmFinal(Integer kmFinal) { this.kmFinal = kmFinal; }

    public Boolean getCambioNeumatico() { return cambioNeumatico; }
    public void setCambioNeumatico(Boolean cambioNeumatico) { this.cambioNeumatico = cambioNeumatico; }

    public String getNeumaticoCambiado() { return neumaticoCambiado; }
    public void setNeumaticoCambiado(String neumaticoCambiado) { this.neumaticoCambiado = neumaticoCambiado; }

    public Boolean getUsoAdBlue() { return usoAdBlue; }
    public void setUsoAdBlue(Boolean usoAdBlue) { this.usoAdBlue = usoAdBlue; }

    public String getOtrosGastos() { return otrosGastos; }
    public void setOtrosGastos(String otrosGastos) { this.otrosGastos = otrosGastos; }
}
