package com.uninpahu.uninpahu.application.categoria.dto;

import java.math.BigDecimal;

// DTO para listar categorías (versión resumida)
public record CategoriaListaDTO(
        Long id,
        String tipo,
        BigDecimal descuento
) {}
