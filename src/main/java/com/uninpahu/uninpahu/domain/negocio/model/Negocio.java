package com.uninpahu.uninpahu.domain.negocio.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "negocio")
@EqualsAndHashCode(of = "id")
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false, unique = true)
    private String nombre;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "calificacion", precision = 3, scale = 2)
    private BigDecimal calificacion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

//    // Relación con Usuario (opcional)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
//    private Usuario usuario;

    // Constructor vacío (requerido por JPA)
    public Negocio() {
    }

    // Constructor con parámetros
    public Negocio(String nombre, Long idUsuario, BigDecimal calificacion, String descripcion) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.calificacion = calificacion;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }
// Métodos de utilidad
    public void actualizarDatos(String nombre, String descripcion) {
    if (nombre != null) {
        this.nombre = nombre;
    }
    if (descripcion != null) {
        this.descripcion = descripcion;
    }
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
                '}';
    }
}


