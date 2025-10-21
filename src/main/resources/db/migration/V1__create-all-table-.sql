
-- Tabla EMPRENDEDOR
CREATE TABLE emprendedor (
    id SERIAL PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    correo_electronico VARCHAR(150) NOT NULL,
    codigo_area VARCHAR(5) DEFAULT '57',
    numero_telefono VARCHAR(20) NOT NULL,
    ciudad VARCHAR(100),
    pais VARCHAR(100) DEFAULT 'Colombia',
    fecha_registro TIMESTAMP DEFAULT NOW()
);

-- Tabla NEGOCIO
CREATE TABLE negocio (
    id SERIAL PRIMARY KEY,
    id_emprendedor INT NOT NULL,
    nombre_negocio VARCHAR(150) NOT NULL,
    categoria_producto_servicio VARCHAR(100) NOT NULL,
    descripcion_negocio TEXT,
    nombre_producto_principal VARCHAR(150),
    caracteristicas_principales TEXT,
    palabras_clave TEXT NOT NULL,
    enlace_redes_o_web VARCHAR(255),
    FOREIGN KEY (id_emprendedor) REFERENCES emprendedor(id) ON DELETE CASCADE
);

-- Tabla ESTADO_NEGOCIO
CREATE TABLE estado_negocio (
    id SERIAL PRIMARY KEY,
    id_negocio INT NOT NULL,
    frecuencia_compra_cliente VARCHAR(100),
    gasto_promedio_cliente NUMERIC(12,2),
    metodo_fijacion_precios VARCHAR(100),
    precio_estimado NUMERIC(12,2),
    publico_objetivo TEXT,
    FOREIGN KEY (id_negocio) REFERENCES negocio(id) ON DELETE CASCADE
);

-- Tabla EVALUACION_VALOR
CREATE TABLE evaluacion_valor (
    id SERIAL PRIMARY KEY,
    id_negocio INT NOT NULL,
    nivel_experiencia_emprendedor INT CHECK (nivel_experiencia_emprendedor BETWEEN 1 AND 5),
    expectativas_al_unirse INT CHECK (expectativas_al_unirse BETWEEN 1 AND 5),
    proyeccion_ventas INT CHECK (proyeccion_ventas BETWEEN 1 AND 5),
    interes_colaboracion INT CHECK (interes_colaboracion BETWEEN 1 AND 5),
    logro_en_30_dias BOOLEAN DEFAULT FALSE,
    logro_en_6_meses BOOLEAN DEFAULT FALSE,
    logro_en_12_meses BOOLEAN DEFAULT FALSE,
    comparacion_precios TEXT,
    rango_ventas_mensuales VARCHAR(100),
    crecimiento_2024_2025 NUMERIC(5,2),
    FOREIGN KEY (id_negocio) REFERENCES negocio(id) ON DELETE CASCADE
);