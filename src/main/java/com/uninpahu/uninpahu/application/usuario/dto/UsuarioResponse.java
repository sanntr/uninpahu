package com.uninpahu.uninpahu.application.usuario.dto;

public record UsuarioResponse(
        Long id,
        String nombreUsuario,
        String nombre,
        String apellido,
        String correo,
        String telefono
) {
}
