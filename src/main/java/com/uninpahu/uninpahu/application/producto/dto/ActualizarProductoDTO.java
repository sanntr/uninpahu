package com.uninpahu.uninpahu.application.producto.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

// DTO para actualizar un producto
public record ActualizarProductoDTO(
        @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
        String nombre,

        String descripcion,

        @DecimalMin(value = "0.00", message = "El descuento no puede ser negativo")
        @DecimalMax(value = "100.00", message = "El descuento no puede ser mayor a 100%")
        BigDecimal descuento,

        @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
        BigDecimal precio,

        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        List<Long> categoriasIds
) {}
