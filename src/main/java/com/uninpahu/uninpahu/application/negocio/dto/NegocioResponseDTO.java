package com.uninpahu.uninpahu.application.negocio.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

// DTO para respuesta de negocio
public record NegocioResponseDTO(
        Long id,
        String nombre,
        Long idUsuario,
        BigDecimal calificacion,
        String descripcion,
        String fechaInicio,
        Boolean registradoCamara,
        String direccion,
        String paginaWeb,
        String ciudad,
        byte[] imagen,
        Integer cantidadProductos
) {}
