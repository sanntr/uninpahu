package com.uninpahu.uninpahu.application.negocio.dto;

import java.math.BigDecimal;

// DTO para respuesta de negocio
public record NegocioResponseDTO(
        Long id,
        String nombre,
        Long idUsuario,
        BigDecimal calificacion,
        String descripcion,
        Integer cantidadProductos
) {}
