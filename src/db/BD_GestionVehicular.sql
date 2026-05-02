-- ======================================================
-- PROYECTO: GESTIÓN VEHICULAR
-- DESCRIPCIÓN: ESTRUCTURA COMPLETA (SCHEMA)
-- ======================================================

-- =========================
-- USUARIOS
-- =========================

CREATE TABLE Usuarios (
    id_usuario INT IDENTITY(1,1),

    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(20) DEFAULT 'EMPLEADO',
    estado BIT DEFAULT 0, --se crea el usuario desactivado porque el admin lo tiene que activar manualmente
    debe_cambiar_password BIT DEFAULT 1,  --todo usuario que ingresa por primera vez debe cambiar la contraseña

    -- PK
    CONSTRAINT pk_usuarios PRIMARY KEY (id_usuario),

    -- UNIQUE
    CONSTRAINT uq_usuarios_username UNIQUE (username),

    -- CHECK
    CONSTRAINT chk_usuarios_rol 
        CHECK (rol IN ('ADMIN', 'ENCARGADO', 'EMPLEADO'))
);

-- =========================
-- EMPLEADOS
-- =========================
CREATE TABLE Empleados (
    id_empleado INT IDENTITY(1,1),

    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dui VARCHAR(10) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    departamento VARCHAR(50) NOT NULL,
    fecha_registro DATETIME DEFAULT GETDATE(),
    licencia VARCHAR(20) DEFAULT 'SIN LICENCIA',
    id_usuario INT NULL,

    -- PK
    CONSTRAINT pk_empleados PRIMARY KEY (id_empleado),

    -- UNIQUE
    CONSTRAINT uq_empleados_dui UNIQUE (dui),
    CONSTRAINT uq_empleados_usuario UNIQUE (id_usuario),

    -- FK
    CONSTRAINT fk_empleados_usuario 
        FOREIGN KEY (id_usuario) 
        REFERENCES Usuarios(id_usuario)
);

-- =========================
-- VEHICULOS
-- =========================
CREATE TABLE Vehiculos (
    id_vehiculo INT IDENTITY(1,1),

    marca VARCHAR(50),
    modelo VARCHAR(50),
    placa VARCHAR(20),
    pasajeros INT,
    tipo VARCHAR(50),
    estado VARCHAR(20) DEFAULT 'DISPONIBLE',

    -- PK
    CONSTRAINT pk_vehiculos PRIMARY KEY (id_vehiculo),

    -- UNIQUE
    CONSTRAINT uq_vehiculos_placa UNIQUE (placa),

    -- CHECK
    CONSTRAINT chk_vehiculos_estado 
        CHECK (estado IN ('DISPONIBLE', 'ASIGNADO', 'MANTENIMIENTO', 'INHABILITADO'))
);

-- =========================
-- SOLICITUDES
-- =========================
CREATE TABLE Solicitudes (
    id_solicitud INT IDENTITY(1,1),

    id_empleado INT NOT NULL, -- quien solicita
    id_conductor INT NULL, -- quien maneja
    id_usuario_aprobador INT NULL, -- quien aprueba/rechaza

    fecha_salida DATE NOT NULL,
    fecha_regreso DATE NOT NULL,
    destino VARCHAR(150),
    motivo_viaje VARCHAR(200),
    motivo_respuesta VARCHAR(200),
    pasajeros INT,

    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_estado DATETIME DEFAULT GETDATE(),

    -- CHECKS
    CONSTRAINT chk_solicitudes_fechas 
        CHECK (fecha_regreso >= fecha_salida),

    CONSTRAINT chk_solicitudes_estado 
        CHECK (estado IN ('PENDIENTE', 'APROBADA', 'RECHAZADA', 'FINALIZADA', 'CANCELADA')),

    CONSTRAINT chk_solicitudes_motivo_respuesta 
        CHECK (
            (estado = 'PENDIENTE' AND motivo_respuesta IS NULL)
            OR
            (estado IN ('APROBADA', 'RECHAZADA', 'CANCELADA') 
                AND motivo_respuesta IS NOT NULL 
                AND LTRIM(RTRIM(motivo_respuesta)) <> '')
        ),

	-- PK
    CONSTRAINT pk_solicitudes PRIMARY KEY (id_solicitud),

    -- FK
    CONSTRAINT fk_solicitudes_empleado 
        FOREIGN KEY (id_empleado) REFERENCES Empleados(id_empleado),

    CONSTRAINT fk_solicitudes_conductor 
        FOREIGN KEY (id_conductor) REFERENCES Empleados(id_empleado),

    CONSTRAINT fk_solicitudes_usuario_aprobador 
        FOREIGN KEY (id_usuario_aprobador) REFERENCES Usuarios(id_usuario)
);

-- =========================
-- ASIGNACIONES
-- =========================
CREATE TABLE Asignaciones (
    id_asignacion INT IDENTITY(1,1),
    
	id_solicitud INT NOT NULL, -- una solicitud solo puede tener un vehículo
    id_vehiculo INT NOT NULL,
    id_usuario_asigno INT NOT NULL,
    fecha_asignacion DATETIME DEFAULT GETDATE(),

     -- PK
    CONSTRAINT pk_asignaciones PRIMARY KEY (id_asignacion),

    -- UNIQUE
    CONSTRAINT uq_asignaciones_solicitud UNIQUE (id_solicitud),

    -- FK
    CONSTRAINT fk_asignaciones_solicitud 
        FOREIGN KEY (id_solicitud) REFERENCES Solicitudes(id_solicitud),

    CONSTRAINT fk_asignaciones_vehiculo 
        FOREIGN KEY (id_vehiculo) REFERENCES Vehiculos(id_vehiculo),

    CONSTRAINT fk_asignaciones_usuario 
        FOREIGN KEY (id_usuario_asigno) REFERENCES Usuarios(id_usuario)
);

-- =========================
-- USO / DEVOLUCION VEHICULO
-- =========================
CREATE TABLE UsoVehiculo (
	id_uso INT IDENTITY(1,1),

    id_asignacion INT NOT NULL,
    kilometraje_salida DECIMAL(10,2),
    kilometraje_regreso DECIMAL(10,2),
    observaciones VARCHAR(200),
    fecha_devolucion DATETIME,

    -- PK
    CONSTRAINT pk_uso_vehiculo PRIMARY KEY (id_uso),

    -- UNIQUE
    CONSTRAINT uq_uso_asignacion UNIQUE (id_asignacion),

    -- FK
    CONSTRAINT fk_uso_asignacion 
        FOREIGN KEY (id_asignacion) REFERENCES Asignaciones(id_asignacion)
);

-- =========================
-- TRIGGERS
-- =========================

-- CREAR USUARIO AUTOMATICO
GO
CREATE TRIGGER trg_crear_usuario_empleado
ON Empleados
AFTER INSERT
AS
BEGIN
    DECLARE @id_empleado INT
    DECLARE @nombres VARCHAR(100)
    DECLARE @apellidos VARCHAR(100)
    DECLARE @username VARCHAR(50)
    DECLARE @base_username VARCHAR(50)
    DECLARE @contador INT = 0

    SELECT 
        @id_empleado = id_empleado,
        @nombres = nombres,
        @apellidos = apellidos
    FROM inserted;

    SET @base_username = LOWER(
        LEFT(@nombres, CHARINDEX(' ', @nombres + ' ') - 1)
        + '.' +
        LEFT(@apellidos, CHARINDEX(' ', @apellidos + ' ') - 1)
    );

    SET @username = @base_username;

    WHILE EXISTS (SELECT 1 FROM Usuarios WHERE username = @username)
    BEGIN
        SET @contador = @contador + 1;
        SET @username = @base_username + CAST(@contador AS VARCHAR);
    END

    INSERT INTO Usuarios (username, password, rol)
    VALUES (
        @username,
        CONVERT(VARCHAR(64), HASHBYTES('SHA2_256', '1234'), 2),
        'EMPLEADO'
    );

    DECLARE @id_usuario INT = SCOPE_IDENTITY();

    UPDATE Empleados
    SET id_usuario = @id_usuario
    WHERE id_empleado = @id_empleado;
END;


-- BLOQUEAR CAMBIO DE USUARIO
GO
CREATE TRIGGER trg_no_cambiar_usuario
ON Empleados
INSTEAD OF UPDATE
AS
BEGIN
    IF UPDATE(id_usuario)
    BEGIN
        IF EXISTS (
            SELECT 1
            FROM Empleados e
            JOIN inserted i ON e.id_empleado = i.id_empleado
            WHERE e.id_usuario IS NOT NULL
              AND i.id_usuario <> e.id_usuario
        )
        BEGIN
            RAISERROR('No se puede cambiar el usuario una vez asignado', 16, 1);
            ROLLBACK;
            RETURN;
        END
    END

    UPDATE Empleados
    SET 
        nombres = i.nombres,
        apellidos = i.apellidos,
        dui = i.dui,
        telefono = i.telefono,
        cargo = i.cargo,
        departamento = i.departamento,
        licencia = i.licencia,
        id_usuario = ISNULL(e.id_usuario, i.id_usuario)
    FROM Empleados e
    JOIN inserted i ON e.id_empleado = i.id_empleado;
END;


-- CAMBIAR ESTADO VEHICULO A ASIGNADO
GO
CREATE TRIGGER trg_asignar_vehiculo
ON Asignaciones
AFTER INSERT
AS
BEGIN
    UPDATE Vehiculos
    SET estado = 'ASIGNADO'
    WHERE id_vehiculo IN (SELECT id_vehiculo FROM inserted);
END;


-- DEVOLUCION VEHICULO
GO
CREATE TRIGGER trg_devolucion
ON UsoVehiculo
AFTER INSERT
AS
BEGIN
    -- vehículo disponible
    UPDATE Vehiculos
    SET estado = 'DISPONIBLE'
    WHERE id_vehiculo IN (
        SELECT a.id_vehiculo
        FROM Asignaciones a
        JOIN inserted i ON a.id_asignacion = i.id_asignacion
    );

    -- solicitud finalizada
    UPDATE Solicitudes
    SET estado = 'FINALIZADA'
    WHERE id_solicitud IN (
        SELECT a.id_solicitud
        FROM Asignaciones a
        JOIN inserted i ON a.id_asignacion = i.id_asignacion
    );
END;

-- ======================================================
--  DATOS DE PRUEBA (SEEDERS)
-- ======================================================
-- 1. INSERT DE EMPLEADOS
-- Nota 1: Se insertan registros uno por uno debido a que el trigger solo maneja una fila a la vez
-- Nota 2: Algunos con licencia para que puedan ser conductores
INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento, licencia)
VALUES ('Maria Fernanda', 'Lopez Diaz', '12345678-9', '7777-7777', 'Chofer', 'Logistica', 'PESADA');

INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento, licencia)
VALUES ('Jose Edwin', 'Segura Castillo', '11111111-1', '7000-0001', 'Chofer', 'Logistica', 'LIVIANA');

INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento)
VALUES ('Ana Lucia', 'Perez Martinez', '22222222-2', '7000-0002', 'Tecnico', 'Mantenimiento');

INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento)
VALUES ('Ivonne Estefany', 'Delgado Landaverde', '33333333-3', '7000-0003', 'Jefe', 'Informatica');

INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento, licencia)
VALUES ('Luis Fernando', 'Castro Lopez', '44444444-4', '7000-0004', 'Chofer', 'Logistica', 'LIVIANA');

INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento)
VALUES ('Himer Alexis', 'Gonzalez Pineda', '13456782-5', '7000-0005', 'Supervisor', 'Mantenimiento');

INSERT INTO Empleados (nombres, apellidos, dui, telefono, cargo, departamento)
VALUES ('Carlos Roberto', 'Gomez Rivas', '55555555-5', '7000-0006', 'Asistente', 'Ventas');

-- 2. ACTIVAR USUARIOS Y ASIGNAR ROLES
-- Admin (Ivonne)
UPDATE Usuarios SET rol = 'ADMIN', estado = 1, debe_cambiar_password = 0 
WHERE username LIKE 'ivonne.delgado%';

-- Encargado (Himer)
UPDATE Usuarios SET rol = 'ENCARGADO', estado = 1, debe_cambiar_password = 0 
WHERE username LIKE 'himer.gonzalez%';

-- Empleado Activo (Carlos) para probar login de usuario común
UPDATE Usuarios SET rol = 'EMPLEADO', estado = 1, debe_cambiar_password = 0 
WHERE username LIKE 'carlos.gomez%';

-- 3. INSERT DE VEHICULOS
INSERT INTO Vehiculos (marca, modelo, placa, pasajeros, tipo, estado)
VALUES
('Toyota', 'Hilux', 'P123-487', 5, 'Pickup', 'DISPONIBLE'),
('Nissan', 'Frontier', 'P905-567', 5, 'Pickup', 'DISPONIBLE'),
('Hyundai', 'Accent', 'P110-678', 5, 'Sedan', 'DISPONIBLE'),
('Kia', 'Sportage', 'P456-789', 5, 'Camioneta', 'INHABILITADO'),
('Isuzu', 'D-Max', 'P567-890', 5, 'Pickup', 'MANTENIMIENTO');

-- 4. INSERT DE SOLICITUDES (Con Lógica de Licencias)
-- Regla 1: Solo 'EMPLEADO' solicita. 
-- Regla 2: Si el solicitante tiene licencia, se pone él mismo; si no, se asigna otro conductor.

INSERT INTO Solicitudes 
(id_empleado, id_conductor, fecha_salida, fecha_regreso, destino, motivo_viaje, pasajeros, estado)
VALUES
-- Maria (tiene licencia, ella conduce)
(1, 1, '2026-06-05', '2026-06-07', 'San Miguel', 'Distribución de suministros', 1, 'PENDIENTE'),

-- Jose (tiene licencia, el conduce)
(2, 2, '2026-06-08', '2026-06-09', 'Usulután', 'Entrega de repuestos', 2, 'PENDIENTE'),

-- Ana (Sin licencia, Maria conduce)
(3, 1, '2026-06-10', '2026-06-10', 'Santa Ana', 'Visita técnica', 1, 'PENDIENTE'),

-- Carlos (Sin licencia, Jose conduce)
(7, 2, '2026-06-12', '2026-06-13', 'Ahuachapán', 'Entrega de documentos', 2, 'PENDIENTE'),

-- Carlos otra (Sin licencia, Luis conduce)
(7, 5, '2026-06-20', '2026-06-21', 'Sonsonate', 'Revisión de clientes', 3, 'PENDIENTE'),

-- Luis (tiene licencia, el conduce)
(5, 5, '2026-06-15', '2026-06-16', 'La Libertad', 'Supervisión de rutas', 2, 'PENDIENTE');

GO
SELECT 'Seeders cargados con éxito' as Mensaje;