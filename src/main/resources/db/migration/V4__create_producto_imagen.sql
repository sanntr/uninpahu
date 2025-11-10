-- Creación de tabla producto_imagen para guardar múltiples imágenes por producto
CREATE TABLE producto_imagen (
    id SERIAL PRIMARY KEY,
    id_producto INT,
    imagen BYTEA,
    FOREIGN KEY (id_producto) REFERENCES producto(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
