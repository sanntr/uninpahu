package com.uninpahu.uninpahu.domain.usuario.repository;

import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RepositoryUsuario extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
