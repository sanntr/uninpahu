package com.uninpahu.uninpahu.application.negocio.dto;

import jakarta.validation.constraints.Size;

// DTO para actualizar un negocio
public record ActualizarNegocioDTO(
        @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
        String nombre,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion
) {}