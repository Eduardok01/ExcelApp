package com.ejemplo.excelapp.repository;

import com.ejemplo.excelapp.model.Resumen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumenRepository extends JpaRepository<Resumen, Long> {
}
