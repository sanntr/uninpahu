package com.uninpahu.uninpahu.application.categoria.dto;

import java.math.BigDecimal;

// DTO para respuesta completa de categoría
public record CategoriaResponseDTO(
        Long id,
        String tipo,
        BigDecimal descuento,
        Integer cantidadProductos
) {}
