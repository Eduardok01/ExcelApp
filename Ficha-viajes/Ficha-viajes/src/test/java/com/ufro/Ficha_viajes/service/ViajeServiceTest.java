package com.ufro.Ficha_viajes.service;

import com.ufro.Ficha_viajes.model.*;
import com.ufro.Ficha_viajes.repository.ViajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViajeServiceTest {

    private ViajeRepository viajeRepository;
    private ViajeService viajeService;

    @BeforeEach
    void setUp() {
        viajeRepository = mock(ViajeRepository.class);
        viajeService = new ViajeService(viajeRepository);
    }

    @Test
    void testSaveCalculaIngresosYGastos() {
        Carga carga1 = new Carga(); carga1.setTotalCarga(1000.0);
        Carga carga2 = new Carga(); carga2.setTotalCarga(2000.0);
        Viatico viatico = new Viatico(); viatico.setMonto(500.0);

        Petroleo petroleoIda = new Petroleo(); petroleoIda.setTotal(300.0);
        Petroleo petroleoVuelta = new Petroleo(); petroleoVuelta.setTotal(400.0);

        AdBlue adBlue = new AdBlue(); adBlue.setUso(true); adBlue.setCosto(150.0);
        Neumatico neumatico = new Neumatico(); neumatico.setCambio(true); neumatico.setCosto(100.0);

        Peaje peaje = new Peaje(); peaje.setCosto(200.0);

        Viaje viaje = new Viaje();
        viaje.setCargasIda(List.of(carga1));
        viaje.setCargasVuelta(List.of(carga2));
        viaje.setViaticos(List.of(viatico));
        viaje.setPetroleoIda(petroleoIda);
        viaje.setPetroleoVuelta(petroleoVuelta);
        viaje.setAdBlue(adBlue);
        viaje.setNeumatico(neumatico);
        viaje.setPeajes(List.of(peaje));

        when(viajeRepository.save(any(Viaje.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Viaje resultado = viajeService.save(viaje);

        assertEquals(3000.0, resultado.getIngresosTotales());
        assertEquals(1650.0, resultado.getGastosTotales());
        assertEquals(1350.0, resultado.getGananciaTotal());
    }
}
