package com.uninpahu.uninpahu.application.negocio.dto;

import jakarta.validation.constraints.*;

// DTO para crear un negocio (SIN idUsuario, se toma del JWT)
public record CrearNegocioDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
        String nombre,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion
) {}

