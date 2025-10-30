-- =======================
-- TABLA: rol
-- =======================
CREATE TABLE rol (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- =======================
-- TABLA: usuario
-- =======================
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nombre_usuario VARCHAR(100) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE
);

-- =======================
-- TABLA: rol_usuario
-- =======================
CREATE TABLE rol_usuario (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =======================
-- TABLA: negocio
-- =======================
CREATE TABLE negocio (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL UNIQUE,
    id_usuario INT,
    calificacion DECIMAL(3,2),
    descripcion TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

-- =======================
-- TABLA: categoria
-- =======================
CREATE TABLE categoria (
    id SERIAL PRIMARY KEY,
    descuento DECIMAL(5,2),
    tipo VARCHAR(100) NOT NULL UNIQUE
);

-- =======================
-- TABLA: producto
-- =======================
CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
    id_negocio INT,
    descuento DECIMAL(5,2),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    calificacion DECIMAL(3,2),
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_negocio) REFERENCES negocio(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =======================
-- TABLA: categoria_producto
-- =======================
CREATE TABLE categoria_producto (
    id SERIAL PRIMARY KEY,
    id_categoria INT NOT NULL,
    id_producto INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- =======================
-- TABLA: compra
-- =======================
CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_negocio INT NOT NULL,
    id_producto INT NOT NULL,
    valor_unidad INT NOT NULL,
    cantidad INT,
    valor_pagado DECIMAL(10,2) NOT NULL,
    descuento_aplicado DECIMAL(5,2),
    fecha_hora_compra TIMESTAMP NOT NULL,
    estado_compra VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_negocio) REFERENCES negocio(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
