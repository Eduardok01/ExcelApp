package com.ejemplo.excelapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ViajeResumen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String patente;
    private String chofer;
    private String nro;

    private BigDecimal totalFlete;
    private BigDecimal saldoAnterior;
    private BigDecimal aporteAjuste;
    private BigDecimal aporte2;
    private BigDecimal totalViaje;
    private BigDecimal saldo;
    private Integer diasViaje;
    private BigDecimal viatico;
    private BigDecimal peajes;
    private BigDecimal romana1;
    private BigDecimal cargaHarina;
    private BigDecimal descarga;
    private BigDecimal lavado;
    private BigDecimal vulcanizacion;
    private BigDecimal repuestos;
    private BigDecimal totalGastos;

    private Integer kmSalida;
    private Integer kmLlegada;
    private Integer totalKm;
    private Integer odometro;

    private BigDecimal rendimiento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "viaje_resumen_id")
    private List<Petroleo> petroleos;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "viaje_resumen_id")
    private List<Adblue> adblues;

    private BigDecimal totalSaldoFlete;

    public ViajeResumen() {
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public BigDecimal getTotalFlete() {
        return totalFlete;
    }

    public void setTotalFlete(BigDecimal totalFlete) {
        this.totalFlete = totalFlete;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getAporteAjuste() {
        return aporteAjuste;
    }

    public void setAporteAjuste(BigDecimal aporteAjuste) {
        this.aporteAjuste = aporteAjuste;
    }

    public BigDecimal getAporte2() {
        return aporte2;
    }

    public void setAporte2(BigDecimal aporte2) {
        this.aporte2 = aporte2;
    }

    public BigDecimal getTotalViaje() {
        return totalViaje;
    }

    public void setTotalViaje(BigDecimal totalViaje) {
        this.totalViaje = totalViaje;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Integer getDiasViaje() {
        return diasViaje;
    }

    public void setDiasViaje(Integer diasViaje) {
        this.diasViaje = diasViaje;
    }

    public BigDecimal getViatico() {
        return viatico;
    }

    public void setViatico(BigDecimal viatico) {
        this.viatico = viatico;
    }

    public BigDecimal getPeajes() {
        return peajes;
    }

    public void setPeajes(BigDecimal peajes) {
        this.peajes = peajes;
    }

    public BigDecimal getRomana1() {
        return romana1;
    }

    public void setRomana1(BigDecimal romana1) {
        this.romana1 = romana1;
    }

    public BigDecimal getCargaHarina() {
        return cargaHarina;
    }

    public void setCargaHarina(BigDecimal cargaHarina) {
        this.cargaHarina = cargaHarina;
    }

    public BigDecimal getDescarga() {
        return descarga;
    }

    public void setDescarga(BigDecimal descarga) {
        this.descarga = descarga;
    }

    public BigDecimal getLavado() {
        return lavado;
    }

    public void setLavado(BigDecimal lavado) {
        this.lavado = lavado;
    }

    public BigDecimal getVulcanizacion() {
        return vulcanizacion;
    }

    public void setVulcanizacion(BigDecimal vulcanizacion) {
        this.vulcanizacion = vulcanizacion;
    }

    public BigDecimal getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(BigDecimal repuestos) {
        this.repuestos = repuestos;
    }

    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(BigDecimal totalGastos) {
        this.totalGastos = totalGastos;
    }

    public Integer getKmSalida() {
        return kmSalida;
    }

    public void setKmSalida(Integer kmSalida) {
        this.kmSalida = kmSalida;
    }

    public Integer getKmLlegada() {
        return kmLlegada;
    }

    public void setKmLlegada(Integer kmLlegada) {
        this.kmLlegada = kmLlegada;
    }

    public Integer getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(Integer totalKm) {
        this.totalKm = totalKm;
    }

    public Integer getOdometro() {
        return odometro;
    }

    public void setOdometro(Integer odometro) {
        this.odometro = odometro;
    }

    public BigDecimal getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(BigDecimal rendimiento) {
        this.rendimiento = rendimiento;
    }

    public List<Petroleo> getPetroleos() {
        return petroleos;
    }

    public void setPetroleos(List<Petroleo> petroleos) {
        this.petroleos = petroleos;
    }

    public List<Adblue> getAdblues() {
        return adblues;
    }

    public void setAdblues(List<Adblue> adblues) {
        this.adblues = adblues;
    }

    public BigDecimal getTotalSaldoFlete() {
        return totalSaldoFlete;
    }

    public void setTotalSaldoFlete(BigDecimal totalSaldoFlete) {
        this.totalSaldoFlete = totalSaldoFlete;
    }
}
