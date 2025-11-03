package com.uninpahu.uninpahu.application.producto.dto;

import jakarta.validation.constraints.*;

// DTO para actualizar stock
public record ActualizarStockDTO(
        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock
) {}
