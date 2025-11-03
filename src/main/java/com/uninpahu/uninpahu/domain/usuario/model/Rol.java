package com.uninpahu.uninpahu.domain.usuario.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "rol")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name="nombre")
    String nombre;
    
    // Constructor con parámetros
    public Rol(String nombre) {
        this.nombre = nombre;
    }
    
    // Setters necesarios
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
