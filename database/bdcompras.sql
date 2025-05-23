-- Crear base de datos
DROP DATABASE IF EXISTS bdcompras;
CREATE DATABASE bdcompras CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bdcompras;

-- Tabla: cliente
CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla: producto
CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    url_imagen TEXT, -- Aquí se guarda la ruta o URL de la imagen
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla: carrito
CREATE TABLE carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    estado ENUM('activo', 'confirmado', 'cancelado') DEFAULT 'activo',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla: carrito_detalle
CREATE TABLE carrito_detalle (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_carrito INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Índices adicionales
CREATE INDEX idx_producto_nombre ON producto(nombre);
CREATE INDEX idx_carrito_cliente ON carrito(id_cliente);
CREATE INDEX idx_detalle_carrito ON carrito_detalle(id_carrito);

-- Este es un script de inserción de productos para una base de datos de compras en línea.
INSERT INTO producto (nombre, descripcion, precio, stock, url_imagen, activo) VALUES
('Camiseta Azul', 'Camiseta de algodón azul, talla M', 19.99, 15, '/compras/img/camiseta_azul.jpg', TRUE),
('Pantalón Jeans', 'Pantalón jeans clásico, talla 32', 39.99, 10, '/compras/img/pantalon_jeans.jpg', TRUE),
('Zapatillas Running', 'Zapatillas deportivas para correr', 59.99, 8, '/compras/img/zapatillas_running.jpg', TRUE),
('Mochila Escolar', 'Mochila resistente para estudiantes', 24.99, 20, '/compras/img/mochila_escolar.jpg', TRUE),
('Gorra Negra', 'Gorra ajustable color negro', 9.99, 30, '/compras/img/gorra_negra.jpg', TRUE),
('Reloj Digital', 'Reloj digital resistente al agua', 49.99, 12, '/compras/img/reloj_digital.jpg', TRUE),
('Auriculares Bluetooth', 'Auriculares inalámbricos con estuche', 29.99, 18, '/compras/img/auriculares_bluetooth.jpg', TRUE),
('Cartera Cuero', 'Cartera de cuero genuino', 34.99, 7, '/compras/img/cartera_cuero.jpg', TRUE),
('Cinturón Marrón', 'Cinturón de cuero marrón, talla L', 14.99, 25, '/compras/img/cinturon_marron.jpg', TRUE),
('Camisa Blanca', 'Camisa formal blanca, manga larga', 22.99, 14, '/compras/img/camisa_blanca.jpg', TRUE);

-- ================================
-- Procedimientos almacenados
-- ================================

DELIMITER $$

-- Registrar cliente
CREATE PROCEDURE registrar_cliente(
    IN p_nombre VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_contraseña VARCHAR(255)
)
BEGIN
    INSERT INTO cliente (nombre, email, contraseña)
    VALUES (p_nombre, p_email, p_contraseña);
END $$

-- Iniciar sesión de cliente
CREATE PROCEDURE iniciar_sesion(
    IN p_email VARCHAR(100)
)
BEGIN
    SELECT id_cliente, nombre, contraseña
    FROM cliente
    WHERE email = p_email;
END $$

-- Listar productos activos (para catálogo)
CREATE PROCEDURE listar_productos_activos()
BEGIN
    SELECT id_producto, nombre, descripcion, precio, stock, url_imagen
    FROM producto
    WHERE activo = TRUE AND stock > 0;
END $$

-- Verificar si producto está en el carrito del cliente
CREATE PROCEDURE verificar_producto_en_carrito(
    IN p_id_cliente INT,
    IN p_id_producto INT,
    OUT p_existe BOOLEAN
)
BEGIN
    DECLARE v_id_carrito INT;

    SELECT id_carrito INTO v_id_carrito
    FROM carrito
    WHERE id_cliente = p_id_cliente AND estado = 'activo';

    SELECT COUNT(*) > 0 INTO p_existe
    FROM carrito_detalle
    WHERE id_carrito = v_id_carrito AND id_producto = p_id_producto;
END $$

-- Agregar producto al carrito
CREATE PROCEDURE agregar_producto_a_carrito(
    IN p_id_cliente INT,
    IN p_id_producto INT,
    IN p_cantidad INT
)
BEGIN
    DECLARE v_id_carrito INT;
    DECLARE v_precio DECIMAL(10,2);

    SELECT id_carrito INTO v_id_carrito
    FROM carrito
    WHERE id_cliente = p_id_cliente AND estado = 'activo';

    IF v_id_carrito IS NULL THEN
        INSERT INTO carrito (id_cliente, estado) VALUES (p_id_cliente, 'activo');
        SET v_id_carrito = LAST_INSERT_ID();
    END IF;

    SELECT precio INTO v_precio
    FROM producto
    WHERE id_producto = p_id_producto;

    IF EXISTS (
        SELECT 1 FROM carrito_detalle
        WHERE id_carrito = v_id_carrito AND id_producto = p_id_producto
    ) THEN
        UPDATE carrito_detalle
        SET cantidad = cantidad + p_cantidad
        WHERE id_carrito = v_id_carrito AND id_producto = p_id_producto;
    ELSE
        INSERT INTO carrito_detalle (id_carrito, id_producto, cantidad, precio_unitario)
        VALUES (v_id_carrito, p_id_producto, p_cantidad, v_precio);
    END IF;
END $$

-- Consultar carrito del cliente
CREATE PROCEDURE consultar_carrito(
    IN p_id_cliente INT
)
BEGIN
    DECLARE v_id_carrito INT;

    SELECT id_carrito INTO v_id_carrito
    FROM carrito
    WHERE id_cliente = p_id_cliente AND estado = 'activo';

    SELECT 
        p.id_producto, 
        p.nombre, 
        p.descripcion,
        p.url_imagen,
        cd.cantidad, 
        cd.precio_unitario,
        (cd.cantidad * cd.precio_unitario) AS subtotal
    FROM carrito_detalle cd
    JOIN producto p ON cd.id_producto = p.id_producto
    WHERE cd.id_carrito = v_id_carrito;
END $$

-- Eliminar producto del carrito
CREATE PROCEDURE eliminar_producto_del_carrito(
    IN p_id_cliente INT,
    IN p_id_producto INT
)
BEGIN
    DECLARE v_id_carrito INT;

    SELECT id_carrito INTO v_id_carrito
    FROM carrito
    WHERE id_cliente = p_id_cliente AND estado = 'activo';

    DELETE FROM carrito_detalle
    WHERE id_carrito = v_id_carrito AND id_producto = p_id_producto;
END $$

-- Actualizar cantidad de producto en el carrito
CREATE PROCEDURE actualizar_cantidad_carrito(
    IN p_id_cliente INT,
    IN p_id_producto INT,
    IN p_cantidad INT
)
BEGIN
    DECLARE v_id_carrito INT;

    SELECT id_carrito INTO v_id_carrito
    FROM carrito
    WHERE id_cliente = p_id_cliente AND estado = 'activo';

    IF v_id_carrito IS NOT NULL THEN
        UPDATE carrito_detalle
        SET cantidad = p_cantidad
        WHERE id_carrito = v_id_carrito AND id_producto = p_id_producto;
    END IF;
END $$

-- Confirmar compra
CREATE PROCEDURE confirmar_compra(
    IN p_id_cliente INT,
    OUT p_error BOOLEAN,
    OUT p_id_producto_error INT,
    OUT p_stock_actual INT
)
proc: BEGIN
    DECLARE v_id_carrito INT;
    DECLARE v_id_producto INT;
    DECLARE v_cantidad INT;
    DECLARE v_stock INT;
    DECLARE done INT DEFAULT 0;
    DECLARE cur CURSOR FOR
        SELECT cd.id_producto, cd.cantidad
        FROM carrito_detalle cd
        JOIN carrito c ON cd.id_carrito = c.id_carrito
        WHERE c.id_cliente = p_id_cliente AND c.estado = 'activo';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    SET p_error = FALSE;
    SET p_id_producto_error = NULL;
    SET p_stock_actual = NULL;

    -- Obtener el carrito activo
    SELECT id_carrito INTO v_id_carrito
    FROM carrito
    WHERE id_cliente = p_id_cliente AND estado = 'activo';

    IF v_id_carrito IS NULL THEN
        SET p_error = TRUE;
        SET p_id_producto_error = NULL;
        SET p_stock_actual = NULL;
        LEAVE proc;
    END IF;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_id_producto, v_cantidad;
        IF done THEN
            LEAVE read_loop;
        END IF;

        SELECT stock INTO v_stock FROM producto WHERE id_producto = v_id_producto;
        IF v_stock < v_cantidad THEN
            SET p_error = TRUE;
            SET p_id_producto_error = v_id_producto;
            SET p_stock_actual = v_stock;
            CLOSE cur;
            LEAVE proc;
        END IF;
    END LOOP;
    CLOSE cur;

    -- Si no hubo error, descuenta stock y confirma carrito
    IF NOT p_error THEN
        -- Descontar stock
        OPEN cur;
        SET done = 0;
        read_loop2: LOOP
            FETCH cur INTO v_id_producto, v_cantidad;
            IF done THEN
                LEAVE read_loop2;
            END IF;
            UPDATE producto SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
        END LOOP;
        CLOSE cur;
        -- Confirmar carrito
        UPDATE carrito SET estado = 'confirmado'
        WHERE id_carrito = v_id_carrito;
    END IF;

END $$

DELIMITER ;