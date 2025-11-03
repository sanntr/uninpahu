package com.uninpahu.uninpahu.application.usuario.service;

import com.uninpahu.uninpahu.application.usuario.dto.UsuarioResponse;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public  class DataTransformer {

    public UsuarioResponse mapToUsuarioResponse(Usuario usuario){
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTelefono()
        );
    }
}
