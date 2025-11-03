package com.uninpahu.uninpahu.application.usuario.service;

import com.uninpahu.uninpahu.domain.usuario.model.Rol;
import com.uninpahu.uninpahu.domain.usuario.repository.RepositoryRol;
import com.uninpahu.uninpahu.infraestructure.exception.ValidacionException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolService {

    final RepositoryRol repositoryRol;
    public RolService(RepositoryRol rol) {
        this.repositoryRol = rol;
    }

    public List<Rol> getAllRols(){
       return repositoryRol.findAll();
    }

    public Rol getRolByName(String rolName){
        return repositoryRol.findByNombre(rolName).orElseThrow(()->new ValidacionException("Rol no encontrado"));
    }
}
