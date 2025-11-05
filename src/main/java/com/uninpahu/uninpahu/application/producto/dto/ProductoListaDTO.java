package com.uninpahu.uninpahu.application.producto.dto;

import java.math.BigDecimal;

// DTO para listar productos (versión resumida)
public record ProductoListaDTO(
        Long id,
        String nombre,
        BigDecimal precio,
        BigDecimal precioConDescuento,
        Integer stock,
        BigDecimal calificacion,
        Boolean activo
) {}
