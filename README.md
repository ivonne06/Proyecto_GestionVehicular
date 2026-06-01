# 🚗 Sistema de Gestión Vehicular (SGV)

![Status](https://img.shields.io/badge/Status-Active%20Development-blue)
![Java](https://img.shields.io/badge/Java-25.0.2-red)
![NetBeans](https://img.shields.io/badge/NetBeans-Apache%2027-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Sistema de escritorio desarrollado en **Java Swing** para la administración integral de vehículos institucionales, solicitudes de transporte, asignación de recursos, control de devoluciones y generación de reportes. Implementa control de acceso basado en roles, validaciones de negocio, trazabilidad de procesos y gestión del ciclo completo de utilización de vehículos institucionales.

---

## 📋 Tabla de Contenidos

- [Descripción General](#-descripción-general)
- [Arquitectura](#-arquitectura)
- [Características Principales](#-características-principales)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación-y-configuración)
- [Configuración de la Base de Datos](#-configuración-de-la-base-de-datos)
- [Gestión de Usuarios](#-gestión-de-usuarios)
- [Gestión de Empleados](#-gestión-de-empleados)
- [Gestión de Vehículos](#-gestión-de-vehículos)
- [Gestión de Solicitudes](#-gestión-de-solicitudes)
- [Gestión de Asignaciones](#-gestión-de-asignaciones)
- [Gestión de Devoluciones](#-gestión-de-devoluciones)
- [Historiales](#-historiales)
- [Reportes](#-reportes)
- [Validaciones del Sistema](#-validaciones-del-sistema)
- [Estructura de la Base de Datos](#-estructura-de-la-base-de-datos)
- [Roles y Permisos](#-roles-y-permisos)
- [Seguridad Implementada](#-seguridad-implementada)
- [Troubleshooting](#-troubleshooting)
- [Contribuir](#-contribuir)
- [Desarrolladores](#-desarrolladores)
- [Soporte](#-soporte)

---

## 📌 Descripción General

El **Sistema de Gestión Vehicular (SGV)** centraliza la información de los vehículos institucionales disponibles y gestiona el ciclo de vida completo de una solicitud de transporte, desde su creación hasta su finalización. Ofrece validaciones rigurosas de datos, generación de reportes profesionales en PDF y trazabilidad completa de operaciones.

### Objetivos Principales:
- 🎯 Automatizar la asignación de vehículos y conductores
- 🔐 Implementar validaciones automáticas mediante triggers y lógica de aplicación
- 📊 Proporcionar reportes en tiempo real del estado de solicitudes en formato PDF
- 👤 Controlar acceso basado en roles de empleados
- ✅ Garantizar que solo personal con licencia vigente pueda conducir
- 📱 Validar datos críticos con reglas específicas del país (El Salvador)
- 📈 Mantener historial completo de operaciones y movimientos vehiculares
- 🔄 Gestionar el ciclo completo: solicitud → aprobación → asignación → devolución → finalización

---

## 🏗️ Arquitectura

### Capas Implementadas

| Capa | Descripción | Componentes |
|---|---|---|
| **Presentación** | Interfaz gráfica | Formularios Swing (JInternalFrame) |
| **Negocio** | Lógica aplicativa | Modelos de entidades, validaciones |
| **Acceso a Datos** | Comunicación con BD | DAOs (Data Access Objects) |
| **Base de Datos** | Persistencia | SQL Server con stored procedures y triggers |

### Patrones de Diseño
- **DAO (Data Access Object):** Abstracción de acceso a datos
- **MVC (Model-View-Controller):** Separación de responsabilidades

---

## 🚀 Características Principales

| Característica | Descripción |
|---|---|
| **Gestión de Usuarios** | Autenticación, roles y permisos con cambio obligatorio de contraseña |
| **Mantenimiento de Catálogos** | Gestión completa de empleados y vehículos |
| **Gestión de Solicitudes** | Flujo completo: creación, aprobación, asignación, devolución y finalización |
| **Control de Disponibilidad** | Validación de vehículos y conductores sin conflictos de fechas |
| **Asignación de Vehículos** | Con validaciones de capacidad, disponibilidad y licencia |
| **Control de Devoluciones** | Registro de kilometraje y observaciones |
| **Validaciones Avanzadas** | DUI salvadoreño, teléfono, placa hexadecimal y licencia vigente |
| **Historiales Completos** | Consulta de solicitudes finalizadas, rechazadas y canceladas |
| **Generación de Reportes** | Exportación de datos a PDF con formato profesional |
| **Trazabilidad** | Registro completo de asignaciones, devoluciones y cambios de estado |
| **Control de Acceso** | Permisos basados en roles: Admin, Encargado, Empleado |

---

## 🛠️ Tecnologías Utilizadas

| Componente | Versión | Descripción |
|---|---|---|
| **Java** | 25.0.2 | Lenguaje de programación principal |
| **Apache NetBeans IDE** | 27 | Entorno de desarrollo integrado |
| **Java Swing / AWT** | - | Framework para interfaz gráfica de usuario |
| **SQL Server** | 2019+ | Gestor de base de datos |
| **JDBC Driver** | 13.4.0.jre11 | Conector Java para SQL Server |
| **JCalendar** | 1.4 | Componente de calendario (JDateChooser) |
| **iTextPDF** | 5.5.13.3 | Generación de reportes en formato PDF |
| **Git / GitHub** | - | Control de versiones |

---

## 📋 Requisitos Previos

Antes de instalar el proyecto, asegúrate de tener lo siguiente:

### Sistema Operativo
- Windows 10/11 o superior
- Linux (Ubuntu 20.04+) o macOS (opcional)

### Software Requerido
| Requisito | Versión | Descarga |
|---|---|---|
| **Java Development Kit (JDK)** | 25.0.2 | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) o [OpenJDK](https://openjdk.java.net/) |
| **Apache NetBeans IDE** | 27 | [NetBeans Official](https://netbeans.apache.org/download/) |
| **SQL Server** | 2019 o posterior | [SQL Server Express](https://www.microsoft.com/en-us/sql-server/sql-server-editions-express) |
| **SQL Server Management Studio (SSMS)** | Última versión | [SSMS Download](https://learn.microsoft.com/en-us/sql/ssms/download-sql-server-management-studio-ssms) |

### Dependencias Java (Incluidas en el Proyecto)
- **JDBC Driver:** 13.4.0.jre11
- **JCalendar:** 1.4
- **iTextPDF:** 5.5.13.3

---

## ⚙️ Instalación y Configuración

### 1️⃣ Clonar el Repositorio

```bash
git clone https://github.com/ivonne06/Proyecto_GestionVehicular.git
cd Proyecto_GestionVehicular
```

### 2️⃣ Configurar Java y NetBeans

1. **Verificar que Java 25.0.2 esté instalado:**
   ```bash
   java -version
   ```
   Debes ver algo como:
   ```
   java version "25.0.2" 2024-XX-XX
   Java(TM) SE Runtime Environment (build 25.0.2+...)
   ```

2. **Abrir el proyecto en Apache NetBeans 27:**
   - Abre NetBeans
   - Ve a `File` → `Open Project`
   - Selecciona la carpeta del proyecto clonado
   - NetBeans detectará automáticamente que es un proyecto Java

### 3️⃣ Configurar las Librerías en NetBeans

El proyecto ya incluye las siguientes librerías. Si necesitas agregarlas manualmente:

1. **Click derecho en el proyecto** → `Properties`
2. Selecciona `Libraries`
3. Añade las siguientes librerías JAR:
   - `sqljdbc.jar` (JDBC Driver 13.4.0.jre11)
   - `jcalendar-1.4.jar` (JCalendar 1.4)
   - `itextpdf-5.5.13.3.jar` (iTextPDF para generación de reportes)

---

## 🗄️ Configuración de la Base de Datos

### 1️⃣ Crear la Base de Datos

1. **Abre SQL Server Management Studio (SSMS)**
2. **Conecta a tu instancia de SQL Server**
3. **Ejecuta el script SQL proporcionado:**
   - Busca el archivo `BD_GestionVehicular.sql` en el proyecto
   - Copia y pega el contenido en una nueva ventana de queries
   - Ejecuta la consulta completa (F5 o botón Execute)

### 2️⃣ Verificar la Base de Datos

```sql
-- Verifica que la BD fue creada
SELECT name FROM sys.databases WHERE name = 'BD_GestionVehicular';

-- Lista todas las tablas
USE BD_GestionVehicular;
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE';
```

Debes ver las tablas:
- `Usuarios`
- `Empleados`
- `Vehículos`
- `Solicitudes`
- `Asignaciones`
- `DevolucionVehiculo`

### 3️⃣ Configurar la Conexión en el Proyecto

Edita la clase de conexión ubicada en `src/conexion/Conexion.java`:

```java
public class Conexion {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BD_GestionVehicular";
    private static final String USER = "sa";  // Tu usuario de SQL Server
    private static final String PASSWORD = "tu_contraseña_aqui";  // Tu contraseña
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a BD_GestionVehicular");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
}
```

**⚠️ Notas Importantes:**
- Reemplaza `localhost` con el nombre de tu servidor si no es local
- Reemplaza `sa` con tu usuario de SQL Server
- Reemplaza `tu_contraseña_aqui` con tu contraseña real
- El puerto por defecto es `1433`

---

## 🔑 Gestión de Usuarios

### Funcionalidades

- ✅ Inicio de sesión seguro
- ✅ Cierre de sesión
- ✅ Cambio obligatorio de contraseña en primer acceso
- ✅ Control de usuarios activos e inactivos
- ✅ Administración de roles
- ✅ Validación de credenciales

### Compilación y Ejecución

1. **Compilar el proyecto:**
   - Click derecho en el proyecto → `Clean and Build` (Shift+F11)
   - Espera a que termine la compilación

2. **Ejecutar la aplicación:**
   - Click en el botón `Run Project` (F6) o
   - Click derecho en el proyecto → `Run`
   - Se abrirá la ventana de login

### Credenciales de Prueba

| Rol | Usuario | Contraseña | Primer acceso |
|---|---|---|---|
| **Admin** | ivonne.delgado | 1234 | Debe cambiar contraseña |
| **Encargado** | himer.gonzalez | 1234 | Debe cambiar contraseña |
| **Empleado** | carlos.gomez | 1234 | Debe cambiar contraseña |

**⚠️ NOTA:** En el primer acceso, el sistema solicita cambiar la contraseña obligatoriamente.

---

## 👤 Gestión de Empleados

### Funcionalidades

- ✅ Registro de empleados
- ✅ Actualización de información
- ✅ Control de licencias de conducir
- ✅ Relación entre empleado y usuario del sistema
- ✅ Activación y desactivación mediante estado del usuario

### Validaciones

| Validación | Descripción |
|---|---|
| **DUI Único** | No se pueden duplicar DUI salvadoreños |
| **Teléfono** | Formato salvadoreño: `XXXX-XXXX` |
| **Licencia** | Control de vigencia y tipo |
| **Integridad Referencial** | Validación con tabla Usuarios |

### Formato de DUI Salvadoreño

```
XXXXXXXXX-X
```
- **9 dígitos** + **guión** + **1 dígito verificador**
- Ejemplo: `12345678-9`

---

## 🚗 Gestión de Vehículos

### Funcionalidades

- ✅ Registro de vehículos
- ✅ Modificación de información
- ✅ Consulta de disponibilidad
- ✅ Control de capacidad de pasajeros
- ✅ Control de placas institucionales

### Validaciones de Placa Hexadecimal

Se permiten los siguientes formatos:

| Formato | Ejemplo | Válido |
|---|---|---|
| PAAA-AAA | P123-ABC | ✅ |
| PXXX-XXX | P901-D4F | ✅ |
| PABC-123 | PABC-DEF | ❌ (No se permiten formatos mixtos) |

**Reglas:**
- La letra inicial **DEBE SER "P"**
- Las letras permitidas son únicamente **A-F** (hexadecimal)
- No se permiten caracteres especiales
- No se permiten formatos fuera de la especificación

### Validación de Capacidad

| Campo | Restricción |
|---|---|
| **Pasajeros Mínimos** | 2 pasajeros |
| **Pasajeros Máximos** | Sin límite establecido |
| **Tipo de Valor** | Solo enteros positivos |
| **Decimales** | No permitidos |

---

## ✈️ Gestión de Solicitudes

### Creación de Solicitudes

Los empleados pueden registrar solicitudes de transporte indicando:

- **Destino:** Lugar a donde viajarán
- **Motivo del viaje:** Razón del desplazamiento
- **Fecha de salida:** Cuándo parten
- **Fecha de regreso:** Cuándo regresan
- **Cantidad de pasajeros:** Número de personas
- **Conductor asignado:** Quién conducirá (automático si tiene licencia)

### Validaciones de Fechas

| Validación | Descripción |
|---|---|
| **Fecha de Salida** | No puede ser anterior a la fecha actual |
| **Fecha de Regreso** | No puede ser anterior a la fecha de salida |
| **Bloqueo Visual** | El calendario de regreso se bloquea automáticamente |

**Restricciones Visuales:** Al seleccionar una fecha de salida, el calendario de regreso bloquea automáticamente todas las fechas anteriores, permitiendo solo seleccionar fechas válidas.

### Validación de Disponibilidad del Empleado

El sistema verifica que el empleado:
- No posea solicitudes activas que se crucen en fechas

**Estados Considerados:**
- `PENDIENTE`
- `APROBADA`
- `ASIGNADA`

### Asignación de Conductores

#### Empleado con Licencia

Si el solicitante posee licencia:
- Se asigna **automáticamente como conductor**
- No necesita seleccionar otro conductor

#### Empleado sin Licencia

Debe seleccionar un conductor disponible mediante **buscador especializado**:
- El sistema filtra solo empleados con licencia vigente
- Verifica que el conductor no tenga viajes en las mismas fechas

### Validación de Disponibilidad del Conductor

El sistema verifica que el conductor:
- No tenga viajes aprobados o asignados en las mismas fechas

**Estados Considerados:**
- `APROBADA`
- `ASIGNADA`

---

## 📋 Gestión de Solicitudes Administrativas

### Estados de Solicitud

| Estado | Descripción |
|---|---|
| **PENDIENTE** | Solicitud recién creada, esperando aprobación |
| **APROBADA** | Solicitud aprobada, lista para asignación de vehículo |
| **ASIGNADA** | Vehículo asignado, lista para utilizar |
| **FINALIZADA** | Vehículo devuelto, solicitud completada |
| **RECHAZADA** | Solicitud rechazada por encargado/admin |
| **CANCELADA** | Solicitud cancelada por solicitante |

### Aprobación de Solicitudes

Antes de aprobar una solicitud, el sistema valida:
- ✅ Disponibilidad del conductor
- ✅ Ausencia de conflictos de fechas
- ✅ Validación de licencia vigente

### Rechazo de Solicitudes

Permite registrar:
- **Motivo de rechazo** (requerido)
- **Fecha del cambio de estado** (automática)

### Cancelación de Solicitudes

Permite cancelar solicitudes con motivo registrado.

**Restricciones:**
No pueden cancelarse solicitudes en estados:
- `ASIGNADA` (vehículo ya en uso)
- `FINALIZADA` (ya completada)
- `CANCELADA` (ya cancelada)

**Información Registrada:**
- Motivo de cancelación
- Fecha de cancelación

---

## 🎯 Gestión de Asignaciones

### Funcionalidades

- ✅ Asignación de vehículos a solicitudes aprobadas
- ✅ Control de disponibilidad vehicular
- ✅ Registro automático de fecha de asignación
- ✅ Cambio automático de estado de solicitud

### Validaciones

| Validación | Descripción |
|---|---|
| **Vehículo Disponible** | El vehículo no debe estar en mantenimiento o inhabilitado |
| **Compatibilidad de Capacidad** | Capacidad del vehículo ≥ Pasajeros solicitados |
| **Control de Solapamiento** | No puede haber asignaciones superpuestas en fechas |
| **Vehículo Único** | Una solicitud solo puede tener un vehículo asignado |

### Resultado Automático

Al asignar un vehículo:
- **Solicitud:** Cambia a estado `ASIGNADA`
- **Vehículo:** Se marca como `ASIGNADO`

---

## 🔄 Gestión de Devoluciones

### Funcionalidades

- ✅ Registro de devolución de vehículos
- ✅ Actualización de kilometraje (salida y regreso)
- ✅ Observaciones de devolución
- ✅ Liberación automática del vehículo

### Información Registrada

| Campo | Descripción |
|---|---|
| **Kilometraje Salida** | km al momento del viaje |
| **Kilometraje Regreso** | km al regreso |
| **Observaciones** | Notas sobre el estado del vehículo |
| **Fecha Devolución** | Registro automático del sistema |

### Validación de Kilometraje

```
Kilometraje Regreso > Kilometraje Salida
```

### Resultado Automático

Al devolver un vehículo:
- **Solicitud:** Cambia a estado `FINALIZADA`
- **Vehículo:** Cambia a estado `DISPONIBLE`
- **Asignación:** Se cierra con información de kilometraje

---

## 📊 Historiales

### Historial de Solicitudes

Permite consultar solicitudes según su estado final:

| Tipo | Estados Mostrados |
|---|---|
| **Solicitudes Finalizadas** | `FINALIZADA` |
| **Solicitudes Rechazadas** | `RECHAZADA` |
| **Solicitudes Canceladas** | `CANCELADA` |

### Información Mostrada

- **Empleado:** Quién solicitó
- **Conductor:** Quién condujo
- **Destino:** Lugar del viaje
- **Motivo:** Razón del desplazamiento
- **Fechas:** Salida y regreso
- **Estado:** Estado final
- **Motivos de Respuesta:** Razón de rechazo o cancelación

---

## 📄 Reportes

### Módulo de Reportes

**Acceso Exclusivo Para:**
- 👔 Administradores
- 👔 Encargados

**Acceso NO Disponible Para:**
- 👤 Empleados

### Generación de Reportes PDF

El sistema genera reportes profesionales sobre:

| Tipo de Reporte | Contenido |
|---|---|
| **Solicitud de Viaje** | Detalles completos de la solicitud |
| **Asignación de Vehículo** | Información de vehículo y conductor asignado |
| **Historial de Viajes** | Listado de viajes realizados |
| **Uso de Vehículo** | Detalle de kilómetros, observaciones y fechas |
| **Reporte Consolidado** | Resumen de operaciones por período |

### Cómo Generar Reportes

1. Navega a la sección correspondiente (Solicitudes, Vehículos, Historial)
2. Selecciona el registro o rango que deseas reportar
3. Click en `Generar PDF` o `Descargar Reporte`
4. El archivo se descargará automáticamente con el nombre y fecha actual

---

## ✅ Validaciones del Sistema

### Validaciones de Datos Salvadoreños

| Campo | Validación | Formato |
|---|---|---|
| **DUI** | Validación de formato salvadoreño | `XXXXXXXXX-X` (9 dígitos + 1 dígito verificador) |
| **Teléfono** | Validación de teléfono salvadoreño | `XXXX-XXXX` (8 dígitos) |
| **Placa Vehicular** | Validación hexadecimal | `P` + caracteres 0-9 y A-F |
| **Licencia** | Validación de vigencia | Fecha de expiración debe ser futura |

### Validaciones de Negocio

| Validación | Descripción |
|---|---|
| **Disponibilidad de Vehículos** | El vehículo no tiene asignaciones en el rango de fechas |
| **Capacidad de Pasajeros** | Pasajeros ≤ Capacidad del vehículo |
| **Licencia Vigente** | Solo permite asignar conductores con licencia válida |
| **Cambio de Estados** | Flujo correcto: PENDIENTE → APROBADA → ASIGNADA → FINALIZADA |
| **Integridad Referencial** | Triggers automáticos para mantener consistencia |
| **Disponibilidad de Conductores** | Conductor sin viajes superpuestos en fechas |
| **Disponibilidad de Empleados** | Empleado sin solicitudes superpuestas en fechas |

---

## 🗄️ Estructura de la Base de Datos

### Tablas Principales

#### 👥 Tabla: Usuarios
```sql
CREATE TABLE Usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(20) DEFAULT 'EMPLEADO',
    -- ADMIN, ENCARGADO, EMPLEADO
    estado BIT DEFAULT 0,
    -- se crea el usuario desactivado, admin lo activa
    debe_cambiar_password BIT DEFAULT 1
    -- usuario debe cambiar contraseña en primer acceso
);
```

#### 👤 Tabla: Empleados
```sql
CREATE TABLE Empleados (
    id_empleado INT IDENTITY(1,1) PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dui VARCHAR(10) UNIQUE NOT NULL,      -- Formato: XXXXXXXXX-X
    telefono VARCHAR(20) NOT NULL,         -- Formato: XXXX-XXXX
    cargo VARCHAR(50) NOT NULL,
    departamento VARCHAR(50) NOT NULL,
    fecha_registro DATETIME DEFAULT GETDATE(),
    licencia VARCHAR(20) DEFAULT 'SIN LICENCIA',
    id_usuario INT UNIQUE FOREIGN KEY REFERENCES Usuarios(id_usuario)
);
```

#### 🚗 Tabla: Vehículos
```sql
CREATE TABLE Vehiculos (
    id_vehiculo INT IDENTITY(1,1) PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    placa VARCHAR(20) UNIQUE NOT NULL,    -- Formato hexadecimal
    pasajeros INT,
    tipo VARCHAR(50),
    estado VARCHAR(20) DEFAULT 'DISPONIBLE'
    -- DISPONIBLE, ASIGNADO, MANTENIMIENTO, INHABILITADO
);
```

#### ✈️ Tabla: Solicitudes
```sql
CREATE TABLE Solicitudes (
    id_solicitud INT IDENTITY(1,1) PRIMARY KEY,
    id_empleado INT NOT NULL FOREIGN KEY REFERENCES Empleados(id_empleado),
    -- quien solicita
    id_conductor INT FOREIGN KEY REFERENCES Empleados(id_empleado),
    -- quien maneja
    id_usuario_aprobador INT FOREIGN KEY REFERENCES Usuarios(id_usuario),
    -- quien aprueba/rechaza
    fecha_salida DATE NOT NULL,
    fecha_regreso DATE NOT NULL,
    destino VARCHAR(150),
    motivo_viaje VARCHAR(200),
    motivo_respuesta VARCHAR(200),
    pasajeros INT,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_estado DATETIME DEFAULT GETDATE()
    -- PENDIENTE, APROBADA, ASIGNADA, RECHAZADA, FINALIZADA, CANCELADA
);
```

#### 🎯 Tabla: Asignaciones
```sql
CREATE TABLE Asignaciones (
    id_asignacion INT IDENTITY(1,1) PRIMARY KEY,
    id_solicitud INT UNIQUE NOT NULL FOREIGN KEY REFERENCES Solicitudes(id_solicitud),
    -- una solicitud solo puede tener un vehículo
    id_vehiculo INT NOT NULL FOREIGN KEY REFERENCES Vehiculos(id_vehiculo),
    id_usuario_asigno INT NOT NULL FOREIGN KEY REFERENCES Usuarios(id_usuario),
    fecha_asignacion DATETIME DEFAULT GETDATE()
);
```

#### 🔄 Tabla: DevolucionVehiculo
```sql
CREATE TABLE DevolucionVehiculo (
    id_devolucion INT IDENTITY(1,1) PRIMARY KEY,
    id_asignacion INT UNIQUE NOT NULL FOREIGN KEY REFERENCES Asignaciones(id_asignacion),
    kilometraje_salida DECIMAL(10,2) NOT NULL,
    kilometraje_regreso DECIMAL(10,2) NOT NULL,
    observaciones VARCHAR(200),
    fecha_devolucion DATETIME DEFAULT GETDATE()
    -- Validación: kilometraje_regreso > kilometraje_salida
);
```

### Validaciones Automáticas (Triggers)

El sistema incluye triggers para:

- ✅ **Crear usuario automáticamente** cuando se registra un empleado
- ✅ **Bloquear cambio de usuario** una vez asignado a un empleado
- ✅ **Cambiar estado de solicitud a ASIGNADA** cuando se asigna un vehículo
- ✅ **Marcar vehículo como DISPONIBLE** al devolverlo
- ✅ **Marcar solicitud como FINALIZADA** al devolver el vehículo
- ✅ **Validar disponibilidad** de vehículos por rango de fechas
- ✅ **Verificar capacidad** del vehículo vs. número de pasajeros
- ✅ **Validar disponibilidad** de conductores sin viajes superpuestos

### Procedimientos Almacenados

- **sp_asignar_vehiculo:** Valida y asigna un vehículo a una solicitud
- **sp_vehiculos_disponibles_v2:** Lista vehículos disponibles en un rango de fechas
- **sp_reporte_solicitudes:** Genera datos para reportes consolidados

---

## 👥 Roles y Permisos

| Permiso | Admin | Encargado | Empleado |
|---|:---:|:---:|:---:|
| Ver empleados | ✅ | ❌ | ❌ |
| Crear empleado | ✅ | ❌ | ❌ |
| Editar empleado | ✅ | ❌ | ❌ |
| Validar DUI y Teléfono | ✅ | ❌ | ❌ |
| Ver vehículos | ✅ | ✅ | ❌ |
| Crear vehículo | ✅ | ❌ | ❌ |
| Validar placa hexadecimal | ✅ | ❌ | ❌ |
| **Solicitar viaje** | **❌** | ❌ | ✅ |
| Aprobar solicitud | ✅ | ✅ | ❌ |
| Rechazar solicitud | ✅ | ✅ | ❌ |
| Asignar vehículo | ✅ | ✅ | ❌ |
| Consultar historiales | ✅ | ✅ | ✅ |
| Generar reportes | ✅ | ✅ | ❌ |
| Ver reportes de otros | ✅ | ✅ | ❌ |

**⚠️ IMPORTANTE:** Los Administradores NO pueden solicitar viajes, solo gestionar y aprobar

---

## 🔒 Seguridad Implementada

### Control de Acceso

- ✅ Validación de credenciales
- ✅ Control de acceso basado en rol en cada formulario
- ✅ Cierre automático de sesión

### Restricción de Acciones

| Rol | Restricciones |
|---|---|
| **Empleados** | Únicamente gestionan sus solicitudes |
| **Encargados** | Administran operaciones sin acceso a empleados |
| **Administradores** | Acceso completo al sistema |

### Validaciones de Integridad

- ✅ Fechas válidas
- ✅ Disponibilidad de recursos
- ✅ Disponibilidad de conductores
- ✅ Disponibilidad de empleados
- ✅ Restricciones de estado
- ✅ Integridad referencial
- ✅ Contraseñas hasheadas con SHA2_256
- ✅ Cambio obligatorio de contraseña en primer acceso

---

## 🔧 Troubleshooting

### ❌ Problema: "No se encuentra el controlador SQL Server"

**Síntoma:**
```
java.lang.ClassNotFoundException: com.microsoft.sqlserver.jdbc.SQLServerDriver
```

**Solución:**
1. Verifica que `sqljdbc.jar` esté en la carpeta `lib/`
2. En NetBeans: Click derecho → Properties → Libraries
3. Agrega el JAR manualmente si no aparece
4. Haz Click derecho → `Clean and Build`

---

### ❌ Problema: "Error de conexión a la base de datos"

**Síntoma:**
```
com.microsoft.sqlserver.jdbc.SQLServerException: Login failed for user 'sa'
```

**Solución:**
1. **Verifica el servidor:**
   ```bash
   sqlcmd -S localhost -U sa -P tu_contraseña
   1> SELECT @@VERSION
   2> GO
   ```
   
2. **Comprueba que SQL Server está corriendo:**
   - Windows: Services (services.msc) → SQL Server debe estar "Running"

3. **Verifica las credenciales en `src/conexion/Conexion.java`:**
   - Usuario correcto
   - Contraseña correcta
   - Nombre del servidor correcto

4. **Verifica el nombre de la base de datos:**
   ```sql
   SELECT name FROM sys.databases WHERE name = 'BD_GestionVehicular';
   ```

---

### ❌ Problema: "La base de datos no existe"

**Síntoma:**
```
Cannot open database "BD_GestionVehicular"
```

**Solución:**
1. Ejecuta el script `BD_GestionVehicular.sql` en SSMS
2. Verifica que el nombre sea exacto (sensible a mayúsculas)
3. Usa este comando para crear manualmente:
   ```sql
   CREATE DATABASE BD_GestionVehicular;
   ```

---

### ⚠️ Problema: "JCalendar no se abre en NetBeans"

**Síntoma:**
Los componentes de calendario no aparecen en el diseñador visual

**Solución:**
1. Agrega `jcalendar-1.4.jar` a las librerías del proyecto
2. En NetBeans: Tools → Palettes → Swing Palette Manager
3. Importa manualmente desde `lib/jcalendar-1.4.jar`
4. Reinicia NetBeans

---

### ⚠️ Problema: "iTextPDF no genera reportes"

**Síntoma:**
```
java.lang.ClassNotFoundException: com.itextpdf.text.Document
```

**Solución:**
1. Verifica que `itextpdf-5.5.13.3.jar` esté en `lib/`
2. En NetBeans: Click derecho en proyecto → Properties → Libraries
3. Agrega la librería manualmente si falta
4. Limpia y recompila: `Clean and Build`

---

### ❌ Problema: "Validación de DUI fallida"

**Síntoma:**
No puedo registrar empleados con DUI válido

**Solución:**
1. Asegúrate de usar el formato correcto: `XXXXXXXXX-X` (9 dígitos + guión + 1 dígito)
2. Ejemplo válido: `12345678-9`
3. Verifica que no haya espacios adicionales
4. El DUI debe ser único en el sistema

---

### ❌ Problema: "Validación de teléfono fallida"

**Síntoma:**
```
Formato de teléfono inválido
```

**Solución:**
1. Usa el formato: `XXXX-XXXX` (4 dígitos + guión + 4 dígitos)
2. Ejemplo válido: `2511-2345`
3. Solo se aceptan dígitos en los campos numéricos

---

### ❌ Problema: "Validación de placa hexadecimal fallida"

**Síntoma:**
No puedo registrar vehículos con placa válida

**Solución:**
1. La placa debe empezar con **"P"**
2. Solo debe contener caracteres hexadecimales (0-9, A-F)
3. Formato: `PXXX-XXX` o `PABC-DEF`
4. Ejemplo válido: `P123-ABC` o `PDEF-456`
5. No se permiten espacios ni caracteres especiales

---

### 🔍 Verificación de Configuración Completa

Ejecuta este código en tu aplicación para verificar todo está correcto:

```java
public class VerificacionSistema {
    public static void main(String[] args) {
        System.out.println("=== VERIFICACIÓN DEL SISTEMA ===");
        
        // Verificar Java
        System.out.println("Java Version: " + System.getProperty("java.version"));
        
        // Verificar JDBC
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("✅ JDBC Driver: Cargado correctamente");
        } catch (Exception e) {
            System.out.println("❌ JDBC Driver: " + e.getMessage());
        }
        
        // Verificar iTextPDF
        try {
            Class.forName("com.itextpdf.text.Document");
            System.out.println("✅ iTextPDF: Cargado correctamente");
        } catch (Exception e) {
            System.out.println("❌ iTextPDF: " + e.getMessage());
        }
        
        // Verificar Conexión BD
        Connection conn = Conexion.getConexion();
        if (conn != null) {
            System.out.println("✅ Base de Datos: Conectada");
        } else {
            System.out.println("❌ Base de Datos: Desconectada");
        }
    }
}
```

---

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Para contribuir al proyecto:

### Pasos para Contribuir

1. **Fork el repositorio** en GitHub

2. **Crea una rama para tu feature:**
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```

3. **Realiza tus cambios** y comitea:
   ```bash
   git add .
   git commit -m "Agrega nueva funcionalidad: descripción"
   ```

4. **Push a tu rama:**
   ```bash
   git push origin feature/nueva-funcionalidad
   ```

5. **Abre un Pull Request** en el repositorio original

### Normas de Código

- Sigue la convención de nombres Java estándar
- Comenta el código complejo
- Escribe commits descriptivos
- Prueba los cambios antes de hacer PR
- Documenta nuevas validaciones en el README

---

## 👨‍💻 Desarrolladores

| Nombre | Rol | Institución |
|---|---|---|
| **Ivonne Delgado** | Desarrolladora | UTEC - Ingeniería en Sistemas |
| **Himer González** | Desarrollador | UTEC - Ingeniería en Sistemas |
| **Jose Segura** | Desarrollador | UTEC - Ingeniería en Sistemas |

**Institución:** Universidad Tecnológica de El Salvador (UTEC)  
**Carrera:** Ingeniería en Sistemas y Computación

---

## 📞 Soporte

Si encuentras problemas o tienes preguntas:

- 📧 **Email:** ivonne06@github.com
- 🐛 **Reportar Bug:** [Issues en GitHub](https://github.com/ivonne06/Proyecto_GestionVehicular/issues)
- 💬 **Discusiones:** [GitHub Discussions](https://github.com/ivonne06/Proyecto_GestionVehicular/discussions)

---

## 📜 Licencia

Este proyecto está bajo la licencia **MIT**. Ver archivo `LICENSE` para más detalles.

---

## 📝 Changelog

### v2.0.0 (2026-06-01) 🚀
- ✅ **Nuevas validaciones:** DUI salvadoreño, teléfono, placa hexadecimal
- ✅ **Generación de reportes:** Integración de iTextPDF 5.5.13.3
- ✅ **Gestión de devoluciones:** Control completo de devoluciones con kilometraje
- ✅ **Historial mejorado:** Consulta de solicitudes finalizadas, rechazadas y canceladas
- ✅ **Cancelación de solicitudes:** Con motivo registrado
- ✅ **Disponibilidad avanzada:** Validación de conductores y empleados
- ✅ **Estados mejorados:** Flujo completo de solicitudes
- ✅ **Trazabilidad mejorada:** Registro de todas las operaciones

### v1.0.0 (2026-05-06)
- ✅ Funcionalidad base de gestión vehicular
- ✅ Sistema de solicitudes de viaje con validaciones
- ✅ Validación de roles y permisos (ADMIN, ENCARGADO, EMPLEADO)
- ✅ Asignación de vehículos con validación de fechas y capacidad
- ✅ Triggers automáticos para manejo de estados
- ✅ Procedimientos almacenados para operaciones críticas
- ✅ Cambio obligatorio de contraseña en primer acceso

---

**Última actualización:** 1 de junio de 2026  
**Estado del Proyecto:** En Desarrollo Activo 🚀
