package com.uninpahu.uninpahu.application.usuario.service;

import com.uninpahu.uninpahu.application.usuario.dto.CrearUsuario;
import com.uninpahu.uninpahu.application.usuario.dto.UsuarioResponse;
import com.uninpahu.uninpahu.domain.usuario.model.Rol;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import com.uninpahu.uninpahu.domain.usuario.repository.RepositoryRol;
import com.uninpahu.uninpahu.domain.usuario.repository.RepositoryUsuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioCreationService {
    final RepositoryUsuario repositoryUsuario;
    final RolService rolService;
    final PasswordEncoder passwordEncoder;
    private DataTransformer dataTransformer = new DataTransformer();

    public UsuarioCreationService(RepositoryUsuario repositoryUsuario, PasswordEncoder passwordEncoder, RolService rolService) {
        this.repositoryUsuario = repositoryUsuario;
        this.passwordEncoder = passwordEncoder;
        this.rolService =rolService;
    }

    public UsuarioResponse create(CrearUsuario datosUsuario){
        Usuario newUsuario=repositoryUsuario.save(mapToUsuario(datosUsuario));
        return dataTransformer.mapToUsuarioResponse(newUsuario);
    }

    //Metodos auxiliares
    private Usuario mapToUsuario(CrearUsuario datosUsuario){
        Set<Rol> rols=new HashSet<>();
        rols.add(addRol("usuario"));
        return new Usuario(
                datosUsuario.nombreUsuario(),
                datosUsuario.nombre(),
                datosUsuario.apellido(),
                passwordEncoder.encode(datosUsuario.contrasena()),
                datosUsuario.correo(),
                datosUsuario.telefono(),
                rols
        );
    }


    private Rol addRol(String rolName){
        return rolService.getRolByName(rolName);
    }
}
