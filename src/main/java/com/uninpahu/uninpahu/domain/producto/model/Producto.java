package com.uninpahu.uninpahu.domain.producto.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.uninpahu.uninpahu.domain.categoria.model.Categoria;
import com.uninpahu.uninpahu.domain.negocio.model.Negocio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "producto")
@Entity(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @NotBlank
    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "id_negocio")
    private Long idNegocio;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    @Column(name = "descuento", precision = 5, scale = 2)
    private BigDecimal descuento;

    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @NotNull
    @Min(0)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "5.00")
    @Column(name = "calificacion", precision = 3, scale = 2)
    private BigDecimal calificacion;

    @Column(name = "activo")
    private Boolean activo = true;

    // Relación con Negocio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_negocio", insertable = false, updatable = false)
    private Negocio negocio;

    // Relación con Categorías (muchos a muchos a través de categoria_producto)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "categoria_producto",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private List<Categoria> categorias = new ArrayList<>();

    // Imágenes del producto (uno a muchos)
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductImage> imagenes = new ArrayList<>();

    // Constructor personalizado
    public Producto(String nombre, String descripcion, Long idNegocio, BigDecimal descuento,
                    BigDecimal precio, Integer stock, BigDecimal calificacion, Boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idNegocio = idNegocio;
        this.descuento = descuento;
        this.precio = precio;
        this.stock = stock;
        this.calificacion = calificacion;
        this.activo = activo;
    }

    // Métodos de utilidad
    public void actualizarDatos(String nombre, String descripcion, BigDecimal precio, Integer stock) {
        if (nombre != null) {
            this.nombre = nombre;
        }
        if (descripcion != null) {
            this.descripcion = descripcion;
        }
        if (precio != null) {
            this.precio = precio;
        }
        if (stock != null) {
            this.stock = stock;
        }
    }

    public void aplicarDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public void actualizarStock(Integer cantidad) {
        this.stock = cantidad;
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    public void actualizarCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public BigDecimal getPrecioConDescuento() {
        if (descuento != null && descuento.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal porcentajeDescuento = descuento.divide(BigDecimal.valueOf(100));
            BigDecimal montoDescuento = precio.multiply(porcentajeDescuento);
            return precio.subtract(montoDescuento);
        }
        return precio;
    }

    // Helpers para manejar imágenes
    public void addImagen(ProductImage imagen) {
        if (imagen == null) return;
        imagen.setProducto(this);
        this.imagenes.add(imagen);
    }

    public void clearImagenes() {
        this.imagenes.clear();
    }

    public boolean hayStock() {
        return stock != null && stock > 0;
    }


}
