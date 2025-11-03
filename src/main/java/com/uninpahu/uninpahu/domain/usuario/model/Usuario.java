package com.uninpahu.uninpahu.domain.usuario.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@Getter
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotBlank
    @Column(name = "nombre")
    private String nombre;

    @NotBlank
    @Column(name = "apellido")
    private String apellido;

    @NotBlank
    @Column(name = "contrasena")
    private String contrasena;

    @NotBlank
    @Column(name = "correo")
    private String correo;

    @NotBlank
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "activo")
    private Boolean activo;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rol_usuario",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> roles = new HashSet<>();

    // Constructor
    public Usuario(String nombreUsuario, String nombre, String apellido, 
                   String contrasena, String correo, String telefono, Set<Rol> roles) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.correo = correo;
        this.telefono = telefono;
        this.roles=roles;
        this.activo = Boolean.TRUE;
    }

    //Cambiar si el usuario esta activo o no
    public void  cambiarEstado(Boolean estado){
        this.activo=estado;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", activo=" + activo +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + rol.nombre))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.nombreUsuario;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return activo != null && activo;
    }
    
    // Métodos auxiliares para manejo de roles
    public void agregarRol(Rol rol) {
        this.roles.add(rol);
    }
    
    public void removerRol(Rol rol) {
        this.roles.remove(rol);
    }
    
    public boolean tieneRol(String nombreRol) {
        return roles.stream()
                .anyMatch(rol -> rol.nombre.equalsIgnoreCase(nombreRol));
    }
}
