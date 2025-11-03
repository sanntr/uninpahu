package com.uninpahu.uninpahu.domain.producto.repository;

import com.uninpahu.uninpahu.domain.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos por negocio
    List<Producto> findByIdNegocio(Long idNegocio);

    // Buscar productos activos por negocio
    List<Producto> findByIdNegocioAndActivoTrue(Long idNegocio);

    // Buscar productos activos
    List<Producto> findByActivoTrue();

    // Buscar productos por nombre (búsqueda parcial)
    @Query("SELECT p FROM producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.activo = true")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    // Buscar productos con stock disponible
    @Query("SELECT p FROM producto p WHERE p.stock > 0 AND p.activo = true")
    List<Producto> findProductosConStock();

    // Buscar productos con descuento
    @Query("SELECT p FROM producto p WHERE p.descuento IS NOT NULL AND p.descuento > 0 AND p.activo = true")
    List<Producto> findProductosConDescuento();

    // Buscar productos por rango de precio
    @Query("SELECT p FROM producto p WHERE p.precio BETWEEN :precioMin AND :precioMax AND p.activo = true")
    List<Producto> findByPrecioBetween(@Param("precioMin") BigDecimal precioMin, @Param("precioMax") BigDecimal precioMax);

    // Buscar productos con mejor calificación
    @Query("SELECT p FROM producto p WHERE p.calificacion IS NOT NULL AND p.activo = true ORDER BY p.calificacion DESC")
    List<Producto> findTopProductos();

    // Buscar productos por categoría
    @Query("SELECT p FROM producto p JOIN p.categorias c WHERE c.id = :categoriaId AND p.activo = true")
    List<Producto> findByCategoria(@Param("categoriaId") Long categoriaId);

    // Contar productos activos de un negocio
    long countByIdNegocioAndActivoTrue(Long idNegocio);

    // Buscar productos con bajo stock
    @Query("SELECT p FROM producto p WHERE p.stock < :cantidad AND p.activo = true")
    List<Producto> findProductosConBajoStock(@Param("cantidad") Integer cantidad);
}