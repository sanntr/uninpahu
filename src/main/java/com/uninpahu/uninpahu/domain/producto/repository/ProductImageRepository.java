package com.uninpahu.uninpahu.domain.producto.repository;

import com.uninpahu.uninpahu.domain.producto.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductoId(Long productoId);
}
