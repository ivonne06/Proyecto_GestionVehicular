# 🚗 Sistema de Gestión Vehicular y Solicitudes de Viaje

![Status](https://img.shields.io/badge/Status-Active%20Development-blue)
![Java](https://img.shields.io/badge/Java-25.0.2-red)
![NetBeans](https://img.shields.io/badge/NetBeans-Apache%2027-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Una aplicación de escritorio robusta desarrollada en **Java** para optimizar el control de flota vehicular, asignación de conductores y procesamiento de solicitudes de transporte en empresas.

---

## 📋 Tabla de Contenidos

- [Descripción del Proyecto](#-descripción-del-proyecto)
- [Características Principales](#-características-principales)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación-y-configuración)
- [Configuración de la Base de Datos](#-configuración-de-la-base-de-datos)
- [Cómo Usar](#-cómo-usar)
- [Estructura de la Base de Datos](#-estructura-de-la-base-de-datos)
- [Roles y Permisos](#-roles-y-permisos)
- [Troubleshooting](#-troubleshooting)
- [Contribuir](#-contribuir)
- [Desarrolladores](#-desarrolladores)
- [Soporte](#-soporte)

---

## 📌 Descripción del Proyecto

El **Sistema de Gestión Vehicular y Solicitudes de Viaje** centraliza la información de los vehículos disponibles y gestiona el ciclo de vida completo de una solicitud de transporte, desde su creación hasta su aprobación o rechazo. 

### Objetivos Principales:
- 🎯 Automatizar la asignación de vehículos y conductores
- 🔐 Implementar validaciones automáticas mediante triggers
- 📊 Proporcionar reportes en tiempo real del estado de solicitudes
- 👤 Controlar acceso basado en roles de empleados
- ✅ Garantizar que solo personal con licencia vigente pueda conducir

---

## 🚀 Características Principales

| Característica | Descripción |
|---|---|
| **Mantenimiento de Catálogos** | Gestión completa de empleados y vehículos con datos actualizados |
| **Gestión de Solicitudes de Viaje** | Sistema completo de solicitud, aprobación y asignación de vehículos |
| **Validación de Roles** | Control de acceso y permisos basado en el tipo de usuario |
| **Asignación de Vehículos** | Asignación automática con validaciones de disponibilidad y fechas |
| **Validaciones Automáticas** | Triggers en BD para garantizar integridad de datos |
| **Historial de Viajes** | Consulta completa del historial de solicitudes procesadas |
| **Validación de Licencias** | Solo empleados con licencia vigente pueden ser conductores |

---

## 🛠️ Tecnologías Utilizadas

| Componente | Versión | Descripción |
|---|---|---|
| **Java** | 25.0.2 | Lenguaje de programación principal |
| **Apache NetBeans IDE** | 27 | Entorno de desarrollo integrado |
| **Java Swing / AWT** | - | Framework para interfaz gráfica de usuario |
| **SQL Server** | 2019+ | Gestor de base de datos |
| **JDBC Driver** | 13.4.0.jre11 | Conector Java para SQL Server |
| **JCalendar** | 1.4 | Componente de calendario para selección de fechas |
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
- `UsoVehiculo`

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

## 🚀 Cómo Usar

### Compilación y Ejecución en NetBeans

1. **Compilar el proyecto:**
   - Click derecho en el proyecto → `Clean and Build` (Shift+F11)
   - Espera a que termine la compilación

2. **Ejecutar la aplicación:**
   - Click en el botón `Run Project` (F6) o
   - Click derecho en el proyecto → `Run`
   - Se abrirá la ventana principal de la aplicación

### Acceso a la Aplicación

#### 🔑 Credenciales de Prueba

| Rol | Usuario | Contraseña | Acciones |
|---|---|---|---|
| **Admin** | ivonne.delgado | 1234 | Gestionar empleados, vehículos, EXCEPTO solicitar viajes |
| **Encargado** | himer.gonzalez | 1234 | Aprobar/Rechazar solicitudes, asignar vehículos |
| **Empleado** | carlos.gomez | 1234 | Solicitar viajes, ver estado de solicitudes |

### Flujo de Uso

#### 👤 Para Empleados

1. **Iniciar sesión** con tus credenciales
2. **Solicitar un viaje:**
   - Click en `Nueva Solicitud`
   - Completa los campos:
     - **Destino:** Lugar a donde viajarás
     - **Motivo:** Razón del viaje
     - **Número de Pasajeros:** Cantidad de personas
     - **Fecha de Salida:** Cuándo partes
     - **Fecha de Regreso:** Cuándo regresas
   - Click en `Enviar Solicitud`

3. **Ver estado:**
   - Accede a `Mis Solicitudes` para ver el estado (PENDIENTE, APROBADA, ASIGNADA, FINALIZADA)

#### 👔 Para Encargados

1. **Iniciar sesión** con credenciales de encargado
2. **Revisar solicitudes pendientes:**
   - Accede a `Solicitudes Pendientes`
   - Visualiza detalles: empleado, destino, motivo, fechas

3. **Aprobar o Rechazar:**
   - Selecciona una solicitud
   - Click en `Aprobar` para aceptarla
   - O click en `Rechazar` con motivo

4. **Asignar Vehículo:**
   - Si está aprobada, asigna un vehículo disponible
   - El sistema valida disponibilidad por fechas

#### 🔐 Para Administradores

1. **Gestionar empleados:**
   - Agregar nuevos empleados
   - Actualizar datos (nombre, DUI, licencia)
   - Desactivar empleados

2. **Gestionar vehículos:**
   - Registrar nuevos vehículos
   - Actualizar estado (disponible, en mantenimiento, inhabilitado)
   - Ver historial de uso

3. **Procesar solicitudes:**
   - Acceso a todas las funciones de encargado
   - Acceso completo a toda la información

**⚠️ NOTA:** Los administradores NO pueden solicitar viajes directamente

---

## 🗄️ Estructura de la Base de Datos

### Tablas Principales

#### 👥 Tabla: Usuarios
```sql
CREATE TABLE Usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(20) DEFAULT 'EMPLEADO', -- ADMIN, ENCARGADO, EMPLEADO
    estado BIT DEFAULT 0,
    debe_cambiar_password BIT DEFAULT 1
);
```

#### 👤 Tabla: Empleados
```sql
CREATE TABLE Empleados (
    id_empleado INT IDENTITY(1,1) PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dui VARCHAR(10) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    cargo VARCHAR(50),
    departamento VARCHAR(50),
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
    placa VARCHAR(20) UNIQUE NOT NULL,
    pasajeros INT,
    tipo VARCHAR(50),
    estado VARCHAR(20) DEFAULT 'DISPONIBLE' 
    -- Estados: DISPONIBLE, ASIGNADO, MANTENIMIENTO, INHABILITADO
);
```

#### ✈️ Tabla: Solicitudes
```sql
CREATE TABLE Solicitudes (
    id_solicitud INT IDENTITY(1,1) PRIMARY KEY,
    id_empleado INT NOT NULL FOREIGN KEY REFERENCES Empleados(id_empleado),
    id_conductor INT FOREIGN KEY REFERENCES Empleados(id_empleado),
    id_usuario_aprobador INT FOREIGN KEY REFERENCES Usuarios(id_usuario),
    fecha_salida DATE NOT NULL,
    fecha_regreso DATE NOT NULL,
    destino VARCHAR(150),
    motivo_viaje VARCHAR(200),
    motivo_respuesta VARCHAR(200),
    pasajeros INT,
    estado VARCHAR(20) DEFAULT 'PENDIENTE'
    -- Estados: PENDIENTE, APROBADA, ASIGNADA, RECHAZADA, FINALIZADA, CANCELADA
);
```

#### 🎯 Tabla: Asignaciones
```sql
CREATE TABLE Asignaciones (
    id_asignacion INT IDENTITY(1,1) PRIMARY KEY,
    id_solicitud INT UNIQUE NOT NULL FOREIGN KEY REFERENCES Solicitudes(id_solicitud),
    id_vehiculo INT NOT NULL FOREIGN KEY REFERENCES Vehiculos(id_vehiculo),
    id_usuario_asigno INT NOT NULL FOREIGN KEY REFERENCES Usuarios(id_usuario),
    fecha_asignacion DATETIME DEFAULT GETDATE()
);
```

#### 🔄 Tabla: UsoVehiculo
```sql
CREATE TABLE UsoVehiculo (
    id_uso INT IDENTITY(1,1) PRIMARY KEY,
    id_asignacion INT UNIQUE NOT NULL FOREIGN KEY REFERENCES Asignaciones(id_asignacion),
    kilometraje_salida DECIMAL(10,2),
    kilometraje_regreso DECIMAL(10,2),
    observaciones VARCHAR(200),
    fecha_devolucion DATETIME
);
```

### Validaciones Automáticas (Triggers)

El sistema incluye triggers para:
- ✅ Crear usuario automáticamente cuando se registra un empleado
- ✅ Actualizar estado de solicitud a ASIGNADA cuando se asigna un vehículo
- ✅ Marcar vehículo como DISPONIBLE al devolverlo
- ✅ Marcar solicitud como FINALIZADA al devolver el vehículo
- ✅ Validar disponibilidad de vehículos por rango de fechas
- ✅ Verificar capacidad del vehículo vs. número de pasajeros

### Procedimientos Almacenados

El sistema incluye procedimientos para:
- **sp_asignar_vehiculo:** Valida y asigna un vehículo a una solicitud
- **sp_vehiculos_disponibles_v2:** Lista vehículos disponibles en un rango de fechas

---

## 👥 Roles y Permisos

| Permiso | Admin | Encargado | Empleado |
|---|:---:|:---:|:---:|
| Ver empleados | ✅ | ❌ | ❌ |
| Crear empleado | ✅ | ❌ | ❌ |
| Editar empleado | ✅ | ❌ | ❌ |
| Ver vehículos | ✅ | ✅ | ❌ |
| Crear vehículo | ✅ | ❌ | ❌ |
| **Solicitar viaje** | **❌** | ❌ | ✅ |
| Aprobar solicitud | ✅ | ✅ | ❌ |
| Rechazar solicitud | ✅ | ✅ | ❌ |
| Asignar vehículo | ✅ | ✅ | ❌ |
| Ver reportes | ✅ | ✅ | ❌ |
| Ver mi historial | ✅ | ✅ | ✅ |

**⚠️ IMPORTANTE:** Los Administradores NO pueden solicitar viajes, solo gestionar y aprobar

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

### v1.0.0 (2026-05-06)
- ✅ Funcionalidad base de gestión vehicular
- ✅ Sistema de solicitudes de viaje con validaciones
- ✅ Validación de roles y permisos (ADMIN, ENCARGADO, EMPLEADO)
- ✅ Asignación de vehículos con validación de fechas y capacidad
- ✅ Triggers automáticos para manejo de estados
- ✅ Procedimientos almacenados para operaciones críticas

---

**Última actualización:** 6 de mayo de 2026  
**Estado del Proyecto:** En Desarrollo Activo 🚀
