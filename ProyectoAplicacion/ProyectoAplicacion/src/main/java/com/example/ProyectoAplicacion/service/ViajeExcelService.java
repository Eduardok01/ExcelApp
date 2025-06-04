

package com.example.ProyectoAplicacion.service;

import com.example.ProyectoAplicacion.model.Viaje;
import com.example.ProyectoAplicacion.repository.ViajeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ViajeExcelService {

    @Autowired
    private ViajeRepository viajeRepository;

    public void guardarDesdeExcel(MultipartFile archivo) throws IOException {
        try (InputStream inputStream = archivo.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet hoja = workbook.getSheetAt(0);

            // Asumimos que la primera fila es cabecera
            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (fila == null) continue;

                Viaje viaje = new Viaje();
                viaje.setNombreConductor(getString(fila, 0));
                viaje.setRutConductor(getString(fila, 1));
                viaje.setFechaViaje(getLocalDate(fila, 2));
                viaje.setPatente(getString(fila, 3));
                viaje.setTipoCargaIda(getString(fila, 4));
                viaje.setTipoCargaVuelta(getString(fila, 5));
                viaje.setFondoARendir(getBigDecimal(fila, 6));
                viaje.setViaticos(getBigDecimal(fila, 7));
                viaje.setPeaje1(getBigDecimal(fila, 8));
                viaje.setPeaje2(getBigDecimal(fila, 9));
                viaje.setPeaje3(getBigDecimal(fila, 10));
                viaje.setLitrosCombustible(getBigDecimal(fila, 11));
                viaje.setPrecioLitroCombustible(getBigDecimal(fila, 12));
                viaje.setKmInicial(getInteger(fila, 13));
                viaje.setKmFinal(getInteger(fila, 14));
                viaje.setCambioNeumatico(getBoolean(fila, 15));
                viaje.setNeumaticoCambiado(getString(fila, 16));
                viaje.setUsoAdBlue(getBoolean(fila, 17));
                viaje.setOtrosGastos(getString(fila, 18));

                viajeRepository.save(viaje);
            }
        }
    }

    private String getString(Row row, int index) {
        Cell cell = row.getCell(index);
        return (cell != null) ? cell.toString().trim() : null;
    }

    private BigDecimal getBigDecimal(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            return (cell != null) ? new BigDecimal(cell.toString().trim()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getInteger(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            return (cell != null) ? (int) cell.getNumericCellValue() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private Boolean getBoolean(Row row, int index) {
        String val = getString(row, index);
        if (val == null) return null;
        return val.equalsIgnoreCase("sí") || val.equalsIgnoreCase("si") || val.equalsIgnoreCase("true");
    }

    private LocalDate getLocalDate(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell != null && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            } else if (cell != null) {
                return LocalDate.parse(cell.toString().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
