-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS bdcompras;
USE bdcompras;

-- Crear la tabla 'producto'
CREATE TABLE producto (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    foto BLOB,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

-- Insertar datos de ejemplo
INSERT INTO producto (nombre, foto, descripcion, precio, stock) VALUES
('Producto 1', NULL, 'Descripción del producto 1', 10.50, 100),
('Producto 2', NULL, 'Descripción del producto 2', 20.00, 50),
('Producto 3', NULL, 'Descripción del producto 3', 15.75, 75);
