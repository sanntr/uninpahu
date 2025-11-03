package com.uninpahu.uninpahu.application.negocio.dto;

import java.math.BigDecimal;

// DTO para listar negocios (versión resumida)
public record NegocioListaDTO(
        Long id,
        String nombre,
        BigDecimal calificacion,
        Integer cantidadProductos
) {}
