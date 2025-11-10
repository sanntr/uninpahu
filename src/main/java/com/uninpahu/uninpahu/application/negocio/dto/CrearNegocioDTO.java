package com.uninpahu.uninpahu.application.negocio.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

// DTO para crear un negocio (SIN idUsuario, se toma del JWT)
public record CrearNegocioDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
        String nombre,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion,
        String fechaInicio,
        Boolean registradoCamara,
        @Size(max = 255)
        String direccion,
        @Size(max = 255)
        String paginaWeb,
        @Size(max = 100)
        String ciudad,
        MultipartFile imagen
) {}

