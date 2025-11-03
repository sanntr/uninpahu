package com.uninpahu.uninpahu.domain.negocio.repository;

import com.uninpahu.uninpahu.domain.negocio.model.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long> {

    // Buscar por nombre exacto
    Optional<Negocio> findByNombre(String nombre);

    // Buscar negocios de un usuario
    List<Negocio> findByIdUsuario(Long idUsuario);

    // Buscar negocios con calificación mayor o igual a un valor
    List<Negocio> findByCalificacionGreaterThanEqual(BigDecimal calificacion);

    // Buscar negocios por nombre (búsqueda parcial, case insensitive)
    @Query("SELECT n FROM Negocio n WHERE LOWER(n.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Negocio> buscarPorNombre(@Param("nombre") String nombre);

    // Verificar si existe un negocio con ese nombre
    boolean existsByNombre(String nombre);

    // Obtener los mejores negocios (por calificación)
    @Query("SELECT n FROM Negocio n WHERE n.calificacion IS NOT NULL ORDER BY n.calificacion DESC")
    List<Negocio> findTopNegocios();

    // Contar negocios de un usuario
    long countByIdUsuario(Long idUsuario);
}