package com.ufro.Ficha_viajes.service;

import com.ufro.Ficha_viajes.model.Viaje;
import com.ufro.Ficha_viajes.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;

    public ViajeService(ViajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
    }

    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    public Optional<Viaje> getById(Long id) {
        return viajeRepository.findById(id);
    }

    public Viaje save(Viaje viaje) {
        // Evitar null en listas para evitar NullPointerException
        if (viaje.getCargasIda() == null) viaje.setCargasIda(List.of());
        if (viaje.getCargasVuelta() == null) viaje.setCargasVuelta(List.of());
        if (viaje.getViaticos() == null) viaje.setViaticos(List.of());
        if (viaje.getPeajes() == null) viaje.setPeajes(List.of());

        // Calcular ingresos y gastos
        double ingresos = calcularIngresosTotales(viaje);
        double gastos = calcularGastosTotales(viaje);
        double ganancia = ingresos - gastos;

        // Setear campos en entidad
        viaje.setIngresosTotales(ingresos);
        viaje.setGastosTotales(gastos);   // <- Este campo debes agregar en tu entidad Viaje
        viaje.setGananciaTotal(ganancia);

        return viajeRepository.save(viaje);
    }

    public void delete(Long id) {
        viajeRepository.deleteById(id);
    }

    private double calcularIngresosTotales(Viaje viaje) {
        return viaje.getCargasIda().stream()
                .mapToDouble(c -> c.getTotalCarga() != null ? c.getTotalCarga() : 0.0)
                .sum()
                + viaje.getCargasVuelta().stream()
                .mapToDouble(c -> c.getTotalCarga() != null ? c.getTotalCarga() : 0.0)
                .sum();
    }

    private double calcularTotalViaticos(Viaje viaje) {
        return viaje.getViaticos().stream()
                .mapToDouble(v -> v.getMonto() != null ? v.getMonto() : 0.0)
                .sum();
    }

    private double calcularGastoPetroleo(Viaje viaje) {
        double gasto = 0.0;
        if (viaje.getPetroleoIda() != null && viaje.getPetroleoIda().getTotal() != null) {
            gasto += viaje.getPetroleoIda().getTotal();
        }
        if (viaje.getPetroleoVuelta() != null && viaje.getPetroleoVuelta().getTotal() != null) {
            gasto += viaje.getPetroleoVuelta().getTotal();
        }
        return gasto;
    }

    private double calcularGastoAdBlue(Viaje viaje) {
        if (viaje.getAdBlue() != null && viaje.getAdBlue().isUso() && viaje.getAdBlue().getCosto() != null) {
            return viaje.getAdBlue().getCosto();
        }
        return 0.0;
    }

    private double calcularGastoNeumatico(Viaje viaje) {
        if (viaje.getNeumatico() != null && viaje.getNeumatico().isCambio() && viaje.getNeumatico().getCosto() != null) {
            return viaje.getNeumatico().getCosto();
        }
        return 0.0;
    }

    private double calcularGastoPeajes(Viaje viaje) {
        return viaje.getPeajes().stream()
                .mapToDouble(p -> p.getCosto() != null ? p.getCosto() : 0.0)
                .sum();
    }

    private double calcularGastosTotales(Viaje viaje) {
        return calcularTotalViaticos(viaje)
                + calcularGastoPetroleo(viaje)
                + calcularGastoAdBlue(viaje)
                + calcularGastoNeumatico(viaje)
                + calcularGastoPeajes(viaje);
    }
}
