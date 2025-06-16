package com.ufro.Ficha_viajes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufro.Ficha_viajes.model.Viaje;
import com.ufro.Ficha_viajes.service.PdfService;
import com.ufro.Ficha_viajes.service.ViajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViajeRestController.class)
public class ViajeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViajeService viajeService;

    @MockBean
    private PdfService pdfService;

    @Autowired
    private ObjectMapper objectMapper;

    private Viaje viajeEjemplo;

    @BeforeEach
    void setup() {
        viajeEjemplo = new Viaje();
        viajeEjemplo.setId(1L);
        viajeEjemplo.setGananciaTotal(10000.0);
    }

    @Test
    void crearViaje_deberiaRetornar201YCuerpoCorrecto() throws Exception {
        Mockito.when(viajeService.save(any(Viaje.class))).thenReturn(viajeEjemplo);

        mockMvc.perform(post("/api/viajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(viajeEjemplo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.gananciaTotal").value(10000.0));
    }
}
