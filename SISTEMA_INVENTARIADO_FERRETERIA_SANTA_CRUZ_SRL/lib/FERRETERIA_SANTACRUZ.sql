create database ferreteria_t1;
use ferreteria_t1;

-- ============================================================
--  SISTEMA DE INVENTARIADO - FERRETERÍA SANTA CRUZ S.R.L.
--  Universidad Privada del Norte - Proyecto Universitario
--  Base de datos: MySQL / MariaDB
-- ============================================================

-- ============================================================
-- TABLA: usuarios
-- Permite el login al sistema (RF31, RF32)
-- ============================================================
CREATE TABLE usuarios (
    id_usuario     INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    username       VARCHAR(50)  NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    activo         TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME     DEFAULT CURRENT_TIMESTAMP
);
-- ============================================================
-- TABLA: categorias
-- Organiza productos por tipo (RF26)
-- ============================================================
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL UNIQUE,
    descripcion  VARCHAR(255)
);

-- ============================================================
-- TABLA: productos
-- RF01 al RF10 (registro, modificacion, precios, stock)
-- ============================================================
CREATE TABLE productos (
    id_producto    INT AUTO_INCREMENT PRIMARY KEY,
    codigo         VARCHAR(20)   NOT NULL UNIQUE,
    nombre         VARCHAR(150)  NOT NULL,
    descripcion    TEXT,
    id_categoria   INT           NOT NULL,
    precio         DECIMAL(10,2) NOT NULL DEFAULT 0,
    unidad_medida  VARCHAR(30)   NOT NULL,
    stock_actual   INT           NOT NULL DEFAULT 0,
    stock_minimo   INT           NOT NULL DEFAULT 0,
    activo         TINYINT(1)    NOT NULL DEFAULT 1,
    fecha_registro DATETIME      DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria)
);

-- ============================================================
-- TABLA: movimientos
-- RF11 al RF18 (entradas, salidas, historial)
-- ============================================================
CREATE TABLE movimientos (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_producto   INT          NOT NULL,
    tipo          ENUM('entrada', 'salida') NOT NULL,
    cantidad      INT          NOT NULL,
    motivo        VARCHAR(255) NOT NULL,
    fecha         DATETIME     DEFAULT CURRENT_TIMESTAMP,
    id_usuario    INT          NOT NULL,
    stock_antes   INT          NOT NULL,
    stock_despues INT          NOT NULL,
    CONSTRAINT fk_mov_producto FOREIGN KEY (id_producto)
        REFERENCES productos(id_producto),
    CONSTRAINT fk_mov_usuario  FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
);

-- ============================================================
-- VISTA: v_stock_critico
-- RF20, RF21, RF22 - Productos criticos y agotados
-- ============================================================
CREATE VIEW v_stock_critico AS
SELECT
    p.id_producto,
    p.codigo,
    p.nombre,
    c.nombre        AS categoria,
    p.stock_actual,
    p.stock_minimo,
    CASE
        WHEN p.stock_actual = 0                THEN 'AGOTADO'
        WHEN p.stock_actual <= p.stock_minimo  THEN 'CRITICO'
        ELSE 'OK'
    END AS estado
FROM productos p
JOIN categorias c ON p.id_categoria = c.id_categoria
WHERE p.activo = 1
  AND p.stock_actual <= p.stock_minimo;

-- ============================================================
-- VISTA: v_inventario_general
-- RF27, RF30 - Reporte completo del inventario
-- ============================================================
CREATE VIEW v_inventario_general AS
SELECT
    p.id_producto,
    p.codigo,
    p.nombre,
    c.nombre                       AS categoria,
    p.unidad_medida,
    p.precio,
    p.stock_actual,
    p.stock_minimo,
    (p.precio * p.stock_actual)    AS valor_total
FROM productos p
JOIN categorias c ON p.id_categoria = c.id_categoria
WHERE p.activo = 1
ORDER BY c.nombre, p.nombre;

-- ============================================================
-- VISTA: v_movimientos_recientes
-- RF16, RF17, RF18 - Historial de movimientos
-- ============================================================
CREATE VIEW v_movimientos_recientes AS
SELECT
    m.id_movimiento,
    m.fecha,
    p.codigo,
    p.nombre        AS producto,
    c.nombre        AS categoria,
    m.tipo,
    m.cantidad,
    m.motivo,
    m.stock_antes,
    m.stock_despues,
    u.nombre        AS usuario
FROM movimientos m
JOIN productos  p ON m.id_producto  = p.id_producto
JOIN categorias c ON p.id_categoria = c.id_categoria
JOIN usuarios   u ON m.id_usuario   = u.id_usuario
ORDER BY m.fecha DESC;

-- ============================================================
-- DATOS INICIALES
-- ============================================================

-- Usuario del proyecto
INSERT INTO usuarios (nombre, username, password) VALUES
('Jhojan', 'n00311207', '12345678'),
('Angel','n00458156','12345678');

-- Categorias para ferreteria
INSERT INTO categorias (nombre, descripcion) VALUES
('Herramientas Manuales',       'Martillos, destornilladores, llaves, etc.'),
('Herramientas Electricas',     'Taladros, sierras, lijadoras, etc.'),
('Materiales de Construccion',  'Cemento, arena, ladrillos, etc.'),
('Plomeria',                    'Tuberias, llaves de paso, conexiones'),
('Electricidad',                'Cables, interruptores, tomacorrientes'),
('Pinturas y Accesorios',       'Pinturas, brochas, rodillos, thinner'),
('Tornilleria y Fijaciones',    'Tornillos, clavos, pernos, anclajes'),
('Seguridad',                   'Cascos, guantes, lentes de proteccion');

-- Productos de ejemplo
INSERT INTO productos (codigo, nombre, descripcion, id_categoria, precio, unidad_medida, stock_actual, stock_minimo) VALUES
('MART-001',  'Martillo de carpintero 16oz', 'Mango de madera reforzado',    1,  25.50, 'unidad',  15,  5),
('DEST-001',  'Destornillador plano 6"',     'Mango ergonomico',              1,   8.90, 'unidad',  30, 10),
('DEST-002',  'Destornillador estrella 6"',  'Mango ergonomico',              1,   8.90, 'unidad',  28, 10),
('TALAD-001', 'Taladro percutor 500W',       'Incluye set de brocas',         2, 189.90, 'unidad',   5,  2),
('CEM-001',   'Cemento Portland 42.5kg',     'Saco de 42.5 kg',              3,  32.00, 'saco',    50, 20),
('TUB-001',   'Tuberia PVC 1/2" x 3m',      'Para agua fria a presion',      4,   7.50, 'unidad',  40, 15),
('CAB-001',   'Cable electrico 2.5mm TW',   'Precio por metro',              5,   2.20, 'metro',  200, 50),
('PINT-001',  'Pintura latex blanco 1gal',  'Interior y exterior',           6,  45.00, 'galon',   20,  8),
('TORN-001',  'Tornillo autoperforante 1"', 'Caja x 100 unidades',           7,  12.00, 'caja',    35, 10),
('CASC-001',  'Casco de seguridad blanco',  'Certificado ANSI Z89.1',        8,  18.50, 'unidad',   3,  5);

-- Movimientos de ejemplo
INSERT INTO movimientos (id_producto, tipo, cantidad, motivo, id_usuario, stock_antes, stock_despues) VALUES
(1,  'entrada', 15, 'Compra inicial de inventario',      1,  0, 15),
(2,  'entrada', 30, 'Compra inicial de inventario',      1,  0, 30),
(3,  'entrada', 28, 'Compra inicial de inventario',      1,  0, 28),
(5,  'entrada', 50, 'Compra a proveedor Cemento SA',     1,  0, 50),
(7,  'entrada',200, 'Compra cable electrico 200m',       1,  0,200),
(1,  'salida',   2, 'Venta a cliente mostrador',         1, 15, 13),
(5,  'salida',   5, 'Despacho a obra cliente #12',       1, 50, 45),
(10, 'salida',   2, 'Venta cliente obra construccion',   1,  5,  3);

-- ============================================================
-- CONSULTAS DE REFERENCIA PARA EL CODIGO JAVA
-- ============================================================

-- LOGIN (RF31):
-- SELECT id_usuario, nombre
-- FROM usuarios
-- WHERE username = ? AND password = ? AND activo = 1;

-- BUSCAR POR CODIGO (RF04):
-- SELECT p.*, c.nombre AS categoria
-- FROM productos p JOIN categorias c ON p.id_categoria = c.id_categoria
-- WHERE p.codigo = ? AND p.activo = 1;

-- BUSCAR POR NOMBRE PARCIAL (RF05):
-- SELECT * FROM productos WHERE nombre LIKE CONCAT('%', ?, '%') AND activo = 1;

-- TODOS LOS PRODUCTOS (RF06):
-- SELECT * FROM v_inventario_general;

-- REGISTRAR MOVIMIENTO Y ACTUALIZAR STOCK (RF11, RF12, RF08):
-- INSERT INTO movimientos (id_producto, tipo, cantidad, motivo, id_usuario, stock_antes, stock_despues)
--   VALUES (?, ?, ?, ?, ?, ?, ?);
-- UPDATE productos SET stock_actual = ? WHERE id_producto = ?;

-- HISTORIAL POR PRODUCTO (RF25):
-- SELECT * FROM v_movimientos_recientes WHERE codigo = ?;

-- HISTORIAL POR FECHAS (RF17):
-- SELECT * FROM v_movimientos_recientes WHERE fecha BETWEEN ? AND ?;

-- STOCK CRITICO Y AGOTADOS (RF20, RF21, RF22):
-- SELECT * FROM v_stock_critico;

-- RESUMEN INVENTARIO (RF30):
-- SELECT
--   COUNT(*)                                                          AS total_productos,
--   SUM(precio * stock_actual)                                        AS valor_total_inventario,
--   SUM(CASE WHEN stock_actual = 0 THEN 1 ELSE 0 END)                AS productos_agotados,
--   SUM(CASE WHEN stock_actual <= stock_minimo AND stock_actual > 0
--            THEN 1 ELSE 0 END)                                       AS productos_criticos
-- FROM productos WHERE activo = 1;