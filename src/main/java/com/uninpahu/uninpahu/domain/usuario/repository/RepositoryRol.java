package com.uninpahu.uninpahu.domain.usuario.repository;

import com.uninpahu.uninpahu.domain.usuario.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RepositoryRol extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String rol);
}