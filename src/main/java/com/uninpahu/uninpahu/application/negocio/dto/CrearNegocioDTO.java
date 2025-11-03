package com.uninpahu.uninpahu.application.negocio.dto;

import jakarta.validation.constraints.*;

// DTO para crear un negocio
public record CrearNegocioDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
        String nombre,

        @NotNull(message = "El ID de usuario es obligatorio")
        Long idUsuario,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion
) {}

