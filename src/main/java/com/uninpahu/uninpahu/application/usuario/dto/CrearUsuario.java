package com.uninpahu.uninpahu.application.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearUsuario(
        @NotBlank
        String nombreUsuario,
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotBlank
        String contrasena,
        @NotBlank
        String correo,
        @NotBlank
        String telefono
) {
}
