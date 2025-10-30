package com.uninpahu.uninpahu.domain.negocio.model;


import jakarta.persistence.*;

@Entity
@Table(name = "negocio")

public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "nombre")
    String nombre;
}
