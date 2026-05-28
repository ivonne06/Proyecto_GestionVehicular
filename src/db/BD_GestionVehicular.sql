-- ======================================================
-- PROYECTO: GESTIÓN VEHICULAR
-- DESCRIPCIÓN: ESTRUCTURA COMPLETA (SCHEMA)
-- ======================================================

-- =========================
-- CREACIÓN BD
-- =========================
DROP DATABASE IF EXISTS BD_GestionVehicular;
GO

CREATE DATABASE BD_GestionVehicular;
GO

USE BD_GestionVehicular;
GO

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
        CHECK (estado IN ('DISPONIBLE', 'MANTENIMIENTO', 'INHABILITADO'))
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
        CHECK (estado IN ('PENDIENTE', 'APROBADA', 'ASIGNADA', 'RECHAZADA', 'FINALIZADA', 'CANCELADA')),

    CONSTRAINT chk_solicitudes_motivo_respuesta 
		CHECK (
			(estado = 'PENDIENTE' AND motivo_respuesta IS NULL)
			OR
			(
				estado IN (
					'APROBADA',
					'ASIGNADA',
					'RECHAZADA',
					'CANCELADA',
					'FINALIZADA'
				)
				AND motivo_respuesta IS NOT NULL
				AND LTRIM(RTRIM(motivo_respuesta)) <> ''
			)
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
-- DEVOLUCION VEHICULO
-- =========================
CREATE TABLE DevolucionVehiculo (
    id_devolucion INT IDENTITY(1,1),

    id_asignacion INT NOT NULL,

    kilometraje_salida DECIMAL(10,2) NOT NULL,
    kilometraje_regreso DECIMAL(10,2) NOT NULL,

    observaciones VARCHAR(200),

    fecha_devolucion DATETIME DEFAULT GETDATE(),

    -- PK
    CONSTRAINT pk_devolucion_vehiculo 
        PRIMARY KEY (id_devolucion),

    -- UNIQUE
    CONSTRAINT uq_devolucion_asignacion 
        UNIQUE (id_asignacion),

    -- CHECK
    CONSTRAINT chk_devolucion_kilometraje
        CHECK (kilometraje_regreso > kilometraje_salida),

    -- FK
    CONSTRAINT fk_devolucion_asignacion
        FOREIGN KEY (id_asignacion)
        REFERENCES Asignaciones(id_asignacion)
);


-- =========================
-- TRIGGERS
-- =========================

-- CREAR USUARIO AUTOMATICO
DROP TRIGGER IF EXISTS trg_crear_usuario_empleado;
GO

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
DROP TRIGGER IF EXISTS trg_no_cambiar_usuario;
GO

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


-- CAMBIAR ESTADO SOLICITUD A ASIGNADA
DROP TRIGGER IF EXISTS trg_solicitud_asignada;
GO

GO
CREATE TRIGGER trg_solicitud_asignada
ON Asignaciones
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Actualizar estado de la solicitud a ASIGNADA
    UPDATE s
    SET s.estado = 'ASIGNADA'
    FROM Solicitudes s
    INNER JOIN inserted i 
        ON s.id_solicitud = i.id_solicitud;
END;
GO

-- DEVOLUCION VEHICULO
DROP TRIGGER IF EXISTS trg_devolucion;
GO

CREATE TRIGGER trg_devolucion
ON DevolucionVehiculo
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- =========================
    -- VEHICULO DISPONIBLE
    -- =========================
    UPDATE v
    SET v.estado = 'DISPONIBLE'
    FROM Vehiculos v
    INNER JOIN Asignaciones a
        ON v.id_vehiculo = a.id_vehiculo
    INNER JOIN inserted i
        ON a.id_asignacion = i.id_asignacion;

    -- =========================
    -- SOLICITUD FINALIZADA
    -- =========================
    UPDATE s
    SET 
        s.estado = 'FINALIZADA',
        s.fecha_estado = GETDATE()
    FROM Solicitudes s
    INNER JOIN Asignaciones a
        ON s.id_solicitud = a.id_solicitud
    INNER JOIN inserted i
        ON a.id_asignacion = i.id_asignacion;

END;
GO

-- =====================================================
-- PROCEDIMIENTO ALMACENADO
-- =====================================================

-- =========================================
-- SP: ASIGNAR VEHICULO
-- =========================================

IF OBJECT_ID('dbo.sp_asignar_vehiculo', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_asignar_vehiculo;
GO

CREATE PROCEDURE dbo.sp_asignar_vehiculo
    @id_solicitud INT,
    @id_vehiculo INT,
    @id_usuario_asigno INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE 
        @estado_solicitud VARCHAR(20),
        @fecha_salida DATE,
        @fecha_regreso DATE,
        @pasajeros_solicitud INT,
        @capacidad_vehiculo INT;

    -- =========================
    -- VALIDAR SOLICITUD
    -- =========================
    SELECT 
        @estado_solicitud = estado,
        @fecha_salida = fecha_salida,
        @fecha_regreso = fecha_regreso,
        @pasajeros_solicitud = pasajeros
    FROM Solicitudes
    WHERE id_solicitud = @id_solicitud;

    IF @estado_solicitud IS NULL
    BEGIN
        RAISERROR('La solicitud no existe',16,1);
        RETURN;
    END

    IF @estado_solicitud <> 'APROBADA'
    BEGIN
        RAISERROR('La solicitud no está aprobada',16,1);
        RETURN;
    END

    -- =========================
    -- VALIDAR VEHICULO
    -- =========================
    SELECT 
        @capacidad_vehiculo = pasajeros
    FROM Vehiculos
    WHERE id_vehiculo = @id_vehiculo;

    IF @capacidad_vehiculo IS NULL
    BEGIN
        RAISERROR('Vehículo no existe',16,1);
        RETURN;
    END

    -- VALIDAR CAPACIDAD
    IF @capacidad_vehiculo < @pasajeros_solicitud
    BEGIN
        RAISERROR('Capacidad insuficiente',16,1);
        RETURN;
    END

    -- VALIDAR DISPONIBILIDAD (FECHAS)
    IF EXISTS (
        SELECT 1
        FROM Asignaciones a
        JOIN Solicitudes s ON a.id_solicitud = s.id_solicitud
        WHERE a.id_vehiculo = @id_vehiculo
        AND s.estado IN ('APROBADA', 'ASIGNADA')
        AND NOT (
            s.fecha_regreso < @fecha_salida
            OR
            s.fecha_salida > @fecha_regreso
        )
    )
    BEGIN
        RAISERROR('Vehículo no disponible en ese rango de fechas',16,1);
        RETURN;
    END

    -- =====================================
    -- VALIDAR DISPONIBILIDAD DEL CONDUCTOR
    -- =====================================
    IF EXISTS (

        SELECT 1
        FROM Solicitudes s

        WHERE s.id_conductor = (
            SELECT id_conductor
            FROM Solicitudes
            WHERE id_solicitud = @id_solicitud
        )

        AND s.id_solicitud <> @id_solicitud

        AND s.estado IN ('APROBADA', 'ASIGNADA')

        AND NOT (
            s.fecha_regreso < @fecha_salida
            OR
            s.fecha_salida > @fecha_regreso
        )
    )
    BEGIN
        RAISERROR(
            'El conductor ya tiene un viaje asignado en esas fechas',
            16,
            1
        );
        RETURN;
    END

    -- VALIDAR QUE NO ESTÉ YA ASIGNADA
    IF EXISTS (
        SELECT 1 FROM Asignaciones 
        WHERE id_solicitud = @id_solicitud
    )
    BEGIN
        RAISERROR('La solicitud ya tiene un vehículo asignado',16,1);
        RETURN;
    END

    -- INSERTAR ASIGNACION
    INSERT INTO Asignaciones(id_solicitud, id_vehiculo, id_usuario_asigno)
    VALUES(@id_solicitud, @id_vehiculo, @id_usuario_asigno);

END;
GO

-- =========================================
-- SP: VEHICULO DISPONIBLES V2
-- =========================================

IF OBJECT_ID('dbo.sp_vehiculos_disponibles_v2', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_vehiculos_disponibles_v2;
GO

GO

CREATE PROCEDURE sp_vehiculos_disponibles_v2
    @fecha_salida DATE,
    @fecha_regreso DATE,
    @pasajeros INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        v.id_vehiculo,
        v.marca,
        v.modelo,
        v.placa,
        v.pasajeros,
        v.tipo
    FROM Vehiculos v
    WHERE v.pasajeros >= @pasajeros
    AND v.estado NOT IN ('MANTENIMIENTO', 'INHABILITADO')

    AND NOT EXISTS (
        SELECT 1
        FROM Asignaciones a
        JOIN Solicitudes s ON a.id_solicitud = s.id_solicitud
        WHERE a.id_vehiculo = v.id_vehiculo
        AND s.estado IN ('APROBADA', 'ASIGNADA')
        AND NOT (
            s.fecha_regreso < @fecha_salida
            OR
            s.fecha_salida > @fecha_regreso
        )
    );
END;
GO

-- =========================================
-- SP: Reporte Solicitudes
-- =========================================
IF OBJECT_ID('dbo.sp_reporte_solicitudes', 'P') IS NOT NULL
    DROP PROCEDURE dbo.reporte_solicitudes;
GO

GO

CREATE PROCEDURE sp_reporte_solicitudes
    @fechaInicio DATE=NULL,
    @fechaFin DATE=NULL,
    @estado VARCHAR(20)=NULL,
    @empleado INT=NULL
AS
BEGIN

	SELECT COUNT(*) total,

	SUM(CASE WHEN estado='APROBADA' THEN 1 ELSE 0 END) aprobadas,

	SUM(CASE WHEN estado='RECHAZADA' THEN 1 ELSE 0 END) rechazadas,

	SUM(CASE WHEN estado='CANCELADA' THEN 1 ELSE 0 END) canceladas,

	SUM(CASE WHEN estado='FINALIZADA' THEN 1 ELSE 0 END) finalizadas

	FROM Solicitudes

	WHERE	(@fechaInicio IS NULL OR fecha_salida>=@fechaInicio)
	AND	(@fechaFin IS NULL OR fecha_regreso<=@fechaFin)
	AND	(@estado IS NULL OR estado=@estado)
	AND	(@empleado IS NULL OR id_empleado=@empleado)

END
GO

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


-- 2. ACTIVAR TODOS LOS USUARIOS DE SEEDERS
UPDATE Usuarios
SET estado = 1,
    debe_cambiar_password = 0;


-- 3.ASIGNAR ROLES ESPECIALES

-- Admin (Ivonne)
UPDATE Usuarios
SET rol = 'ADMIN'
WHERE username LIKE 'ivonne.delgado%';

-- Encargado (Himer)
UPDATE Usuarios
SET rol = 'ENCARGADO'
WHERE username LIKE 'himer.gonzalez%';

-- 4. INSERT DE VEHICULOS
INSERT INTO Vehiculos (marca, modelo, placa, pasajeros, tipo, estado)
VALUES
('Toyota', 'Hilux', 'P123-487', 5, 'Pickup', 'DISPONIBLE'),
('Nissan', 'Frontier', 'P905-567', 5, 'Pickup', 'DISPONIBLE'),
('Hyundai', 'Accent', 'P110-678', 5, 'Sedan', 'DISPONIBLE'),
('Kia', 'Sportage', 'P456-789', 5, 'Camioneta', 'INHABILITADO'),
('Isuzu', 'D-Max', 'P567-890', 5, 'Pickup', 'MANTENIMIENTO');

-- 6. INSERT DE SOLICITUDES (Con Lógica de Licencias)
-- Regla 1: Solo 'EMPLEADO' solicita. 
-- Regla 2: Si el solicitante tiene licencia, se pone él mismo; si no, se asigna otro conductor.

INSERT INTO Solicitudes 
(id_empleado, id_conductor, fecha_salida, fecha_regreso, destino, motivo_viaje, motivo_respuesta, pasajeros, estado)
VALUES
-- Maria (tiene licencia, ella conduce)
(1, 1, '2026-06-05', '2026-06-07', 'San Miguel', 'Distribución de suministros', NULL, 1, 'PENDIENTE'),

-- Jose (tiene licencia, el conduce)
(2, 2, '2026-06-08', '2026-06-09', 'Usulután', 'Entrega de repuestos', NULL, 2, 'PENDIENTE'),

-- Ana (Sin licencia, Maria conduce)
(3, 1, '2026-06-10', '2026-06-10', 'Santa Ana', 'Visita técnica', NULL, 1, 'PENDIENTE'),

-- Carlos (Sin licencia, Jose conduce)
(7, 2, '2026-06-12', '2026-06-13', 'Ahuachapán', 'Entrega de documentos', NULL, 2, 'PENDIENTE'),

-- Carlos otra (Sin licencia, Luis conduce)
(7, 5, '2026-06-20', '2026-06-21', 'Sonsonate', 'Revisión de clientes', NULL, 3, 'PENDIENTE'),

-- Luis (tiene licencia, el conduce)
(5, 5, '2026-06-15', '2026-06-16', 'La Libertad', 'Supervisión de rutas', NULL, 2, 'PENDIENTE');

GO
SELECT 'Seeders cargados con éxito' as Mensaje;