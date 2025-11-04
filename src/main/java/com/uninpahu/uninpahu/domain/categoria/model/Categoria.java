package com.uninpahu.uninpahu.domain.categoria.model;


import com.uninpahu.uninpahu.domain.producto.model.Producto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "categoria")
@Entity(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    @Column(name = "descuento", precision = 5, scale = 2)
    private BigDecimal descuento;

    @NotBlank
    @Size(max = 100)
    @Column(name = "tipo", length = 100, nullable = false, unique = true)
    private String tipo;

    // Relación con Productos (muchos a muchos)
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();

    // Constructor personalizado
    public Categoria(BigDecimal descuento, String tipo) {
        this.descuento = descuento;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", descuento=" + descuento +
                ", tipo='" + tipo + '\'' +
                ", productos=" + productos +
                '}';
    }
}

