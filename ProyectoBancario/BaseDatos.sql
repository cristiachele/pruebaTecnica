-- Script de base de datos para el sistema bancario
-- Base de datos: bancodb

-- Crear esquema si no existe
CREATE SCHEMA IF NOT EXISTS public;

-- Eliminar tablas si existen (en orden inverso de dependencias)
DROP TABLE IF EXISTS movimientos CASCADE;
DROP TABLE IF EXISTS cuentas CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;
DROP TABLE IF EXISTS personas CASCADE;

-- Crear tabla personas
CREATE TABLE personas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INTEGER,
    identificacion VARCHAR(20) UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(20)
);

-- Crear tabla clientes (hereda de personas)
CREATE TABLE clientes (
    persona_id BIGINT PRIMARY KEY,
    cliente_id VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE
);

-- Crear índice único para cliente_id
CREATE UNIQUE INDEX uk_cliente_id ON clientes(cliente_id);

-- Crear tabla cuentas
CREATE TABLE cuentas (
    id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(15, 2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cuenta_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(persona_id) ON DELETE CASCADE
);

-- Crear índice único para numero_cuenta
CREATE UNIQUE INDEX uk_numero_cuenta ON cuentas(numero_cuenta);

-- Crear tabla movimientos
CREATE TABLE movimientos (
    id BIGSERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(15, 2) NOT NULL,
    saldo DECIMAL(15, 2) NOT NULL,
    cuenta_id BIGINT NOT NULL,
    CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuentas(id) ON DELETE CASCADE
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_movimientos_cuenta_id ON movimientos(cuenta_id);
CREATE INDEX idx_movimientos_fecha ON movimientos(fecha);
CREATE INDEX idx_cuentas_cliente_id ON cuentas(cliente_id);

-- Insertar datos de ejemplo

-- Insertar personas
INSERT INTO personas (nombre, genero, edad, identificacion, direccion, telefono) VALUES
('Jose Lema', 'Masculino', 35, '1234567890', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'Femenino', 28, '2345678901', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'Masculino', 42, '3456789012', '13 junio y Equinoccial', '098874587');

-- Insertar clientes
INSERT INTO clientes (persona_id, cliente_id, contrasena, estado) VALUES
((SELECT id FROM personas WHERE identificacion = '1234567890'), 'CLI001', '1234', true),
((SELECT id FROM personas WHERE identificacion = '2345678901'), 'CLI002', '5678', true),
((SELECT id FROM personas WHERE identificacion = '3456789012'), 'CLI003', '1245', true);

-- Insertar cuentas
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
('478758', 'Ahorro', 2000.00, true, (SELECT persona_id FROM clientes WHERE cliente_id = 'CLI001')),
('225487', 'Corriente', 100.00, true, (SELECT persona_id FROM clientes WHERE cliente_id = 'CLI002')),
('495878', 'Ahorros', 0.00, true, (SELECT persona_id FROM clientes WHERE cliente_id = 'CLI003')),
('496825', 'Ahorros', 540.00, true, (SELECT persona_id FROM clientes WHERE cliente_id = 'CLI002')),
('585545', 'Corriente', 1000.00, true, (SELECT persona_id FROM clientes WHERE cliente_id = 'CLI001'));

-- Insertar movimientos de ejemplo
INSERT INTO movimientos (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
('2022-02-08 10:00:00', 'Débito', -575.00, 1425.00, (SELECT id FROM cuentas WHERE numero_cuenta = '478758')),
('2022-02-10 14:30:00', 'Crédito', 600.00, 700.00, (SELECT id FROM cuentas WHERE numero_cuenta = '225487')),
('2022-02-09 09:15:00', 'Crédito', 150.00, 150.00, (SELECT id FROM cuentas WHERE numero_cuenta = '495878')),
('2022-02-08 16:45:00', 'Débito', -540.00, 0.00, (SELECT id FROM cuentas WHERE numero_cuenta = '496825'));

-- Verificar datos insertados
SELECT 'Personas insertadas: ' || COUNT(*) FROM personas;
SELECT 'Clientes insertados: ' || COUNT(*) FROM clientes;
SELECT 'Cuentas insertadas: ' || COUNT(*) FROM cuentas;
SELECT 'Movimientos insertados: ' || COUNT(*) FROM movimientos;
