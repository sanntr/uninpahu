package com.uninpahu.uninpahu.domain.categoria.repository;

import com.uninpahu.uninpahu.domain.categoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByTipo(String tipo);

    boolean existsByTipo(String tipo);
}