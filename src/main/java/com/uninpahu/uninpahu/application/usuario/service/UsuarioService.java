package com.uninpahu.uninpahu.application.usuario.service;

import com.uninpahu.uninpahu.application.usuario.dto.UsuarioResponse;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import com.uninpahu.uninpahu.domain.usuario.repository.RepositoryUsuario;
import com.uninpahu.uninpahu.infraestructure.exception.ValidacionException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    final RepositoryUsuario repositoryUsuario;
    private DataTransformer dataTransformer = new DataTransformer();
    public UsuarioService(RepositoryUsuario repositoryUsuario) {
        this.repositoryUsuario = repositoryUsuario;
    }

    public UsuarioResponse buscar(Long id) {
        var usuario= repositoryUsuario.findById(id);
        if (usuario.isPresent()){
            return dataTransformer.mapToUsuarioResponse(usuario.get());
        }
        throw new ValidacionException("Usuario no encontrado");
    }


}
