package com.uninpahu.uninpahu.domain.negocio.model;


import com.uninpahu.uninpahu.domain.producto.model.Producto;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "negocio")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false, unique = true)
    private String nombre;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "5.00")
    @Column(name = "calificacion", precision = 3, scale = 2)
    private BigDecimal calificacion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private String fechaInicio;

    @Column(name = "registrado_camara")
    private Boolean registradoCamara = Boolean.FALSE;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "pagina_web", length = 255)
    private String paginaWeb;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    // Relación con Productos (un negocio tiene muchos productos)
    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();

    // Constructor personalizado (sin id y sin relaciones)
    public Negocio(String nombre, Long idUsuario, BigDecimal calificacion, String descripcion,
                   String fechaInicio, Boolean registradoCamara, String direccion,
                   String paginaWeb, String ciudad, byte[] imagen) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.calificacion = calificacion;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.registradoCamara = registradoCamara == null ? Boolean.FALSE : registradoCamara;
        this.direccion = direccion;
        this.paginaWeb = paginaWeb;
        this.ciudad = ciudad;
        this.imagen = imagen;
    }

    // Métodos de utilidad
    public void actualizarDatos(String nombre, String descripcion) {
        if (nombre != null) {
            this.nombre = nombre;
        }
        if (descripcion != null) {
            this.descripcion = descripcion;
        }
    }

    public void actualizarDatosExtendido(String nombre, String descripcion, String fechaInicio,
                                          Boolean registradoCamara, String direccion, String paginaWeb,
                                          String ciudad, byte[] imagen) {
        actualizarDatos(nombre, descripcion);
        if (fechaInicio != null) this.fechaInicio = fechaInicio;
        if (registradoCamara != null) this.registradoCamara = registradoCamara;
        if (direccion != null) this.direccion = direccion;
        if (paginaWeb != null) this.paginaWeb = paginaWeb;
        if (ciudad != null) this.ciudad = ciudad;
        if (imagen != null) this.imagen = imageCopy(imagen);
    }

    // Defensive copy for mutable byte[]
    private static byte[] imageCopy(byte[] src) {
        if (src == null) return null;
        byte[] dst = new byte[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        return dst;
    }

    public void actualizarCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "Negocio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", idUsuario=" + idUsuario +
                ", calificacion=" + calificacion +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", registradoCamara='" + registradoCamara + '\'' +
                ", direccion='" + direccion + '\'' +
                ", paginaWeb='" + paginaWeb + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", imagenLength=" + (imagen != null ? imagen.length : 0) +
                '}';
    }
}


