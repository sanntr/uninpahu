package com.uninpahu.uninpahu.application.producto.dto;

import java.math.BigDecimal;
import java.util.List;

// DTO para respuesta de producto
public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        Long idNegocio,
        String nombreNegocio,
        BigDecimal descuento,
        BigDecimal precio,
        BigDecimal precioConDescuento,
        Integer stock,
        BigDecimal calificacion,
        Boolean activo,
        List<String> categorias,
        List<byte[]> imagenes
) {}
