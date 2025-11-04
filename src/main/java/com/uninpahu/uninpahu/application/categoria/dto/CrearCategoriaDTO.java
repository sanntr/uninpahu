package com.uninpahu.uninpahu.application.categoria.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

// DTO para crear una categoría
public record CrearCategoriaDTO(
        @NotBlank(message = "El tipo es obligatorio")
        @Size(max = 100, message = "El tipo no puede exceder 100 caracteres")
        String tipo,

        @DecimalMin(value = "0.00", message = "El descuento no puede ser negativo")
        @DecimalMax(value = "100.00", message = "El descuento no puede ser mayor a 100%")
        BigDecimal descuento
) {}