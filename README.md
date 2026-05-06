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
- ✅ Garantizar que solo personal operativo con licencia vigente solicite viajes

---

## 🚀 Características Principales

| Característica | Descripción |
|---|---|
| **Mantenimiento de Catálogos** | Gestión completa de empleados y vehículos con datos actualizados |
| **Gestión de Viajes** | Formulario dinámico para registrar destinos, motivos de viaje y número de pasajeros |
| **Validación de Roles** | Control de acceso y permisos basado en el tipo de empleado |
| **Reporte de Solicitudes** | Visualización en tiempo real del estado (PENDIENTE, APROBADO, RECHAZADO) |
| **Validaciones Automáticas** | Triggers en BD para garantizar integridad de datos |
| **Historial de Viajes** | Consulta completa del historial de solicitudes procesadas |
| **Restricciones de Seguridad** | Admins y gerentes no pueden solicitar viajes directamente |

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

**Ubicación esperada de librerías:**
```
Proyecto_GestionVehicular/
├── lib/
│   ├── sqljdbc.jar (JDBC 13.4.0.jre11)
│   └── jcalendar-1.4.jar
└── src/
```

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
- `Empleados`
- `Vehículos`
- `Solicitudes`

### 3️⃣ Configurar la Conexión en el Proyecto

Edita la clase de conexión (generalmente en `src/models/Conexion.java`):

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
| **Administrador** | admin | admin123 | Gestionar empleados, vehículos, aprobar/rechazar viajes |
| **Gerente** | gerente01 | gerente123 | Aprobar/rechazar viajes, ver reportes |
| **Operario** | operario01 | operario123 | Solicitar viajes, ver estado de solicitudes |

### Flujo de Uso

#### 👤 Para Operarios (Personal Operativo)

1. **Iniciar sesión** con tus credenciales
2. **Solicitar un viaje:**
   - Click en `Nueva Solicitud`
   - Completa los campos:
     - **Destino:** Lugar a donde viajarás (txtDestino)
     - **Motivo:** Razón del viaje (trabajo, reunión, etc.)
     - **Número de Pasajeros:** Cantidad de personas
     - **Fecha y Hora:** Cuándo necesitas el vehículo
   - Click en `Enviar Solicitud`

3. **Ver estado:**
   - Accede a `Mis Solicitudes` para ver el estado actual (PENDIENTE, APROBADO, RECHAZADO)

#### 👔 Para Gerentes

1. **Iniciar sesión** con credenciales de gerente
2. **Revisar solicitudes pendientes:**
   - Accede a `Solicitudes Pendientes` (tblSolicitudes)
   - Visualiza detalles: empleado, destino, motivo, fecha

3. **Aprobar o Rechazar:**
   - Selecciona una solicitud
   - Click en `Aprobar` para asignar un vehículo y conductor
   - O click en `Rechazar` con observaciones (opcional)

4. **Ver reportes:**
   - Accede a `Reportes` para análisis de viajes, conductores más utilizados, etc.

#### 🔐 Para Administradores

1. **Gestionar empleados:**
   - Agregar nuevos empleados
   - Actualizar datos (nombre, cédula, licencia)
   - Desactivar empleados

2. **Gestionar vehículos:**
   - Registrar nuevos vehículos
   - Actualizar estado (disponible, en mantenimiento)
   - Ver historial de uso

3. **Procesar solicitudes:**
   - Las mismas funciones que gerentes
   - Acceso completo a toda la información

---

## 🗄️ Estructura de la Base de Datos

### Tablas Principales

#### 📋 Tabla: Empleados
```sql
CREATE TABLE Empleados (
    EmpleadoID INT PRIMARY KEY IDENTITY(1,1),
    Nombre NVARCHAR(100) NOT NULL,
    Cedula NVARCHAR(20) UNIQUE NOT NULL,
    Telefono NVARCHAR(15),
    Email NVARCHAR(100),
    Rol NVARCHAR(50) NOT NULL, -- Admin, Gerente, Operario
    LicenciaVigente BIT NOT NULL DEFAULT 1,
    FechaContratacion DATE,
    Estado BIT NOT NULL DEFAULT 1
);
```

#### 🚗 Tabla: Vehículos
```sql
CREATE TABLE Vehículos (
    VehiculoID INT PRIMARY KEY IDENTITY(1,1),
    Placa NVARCHAR(20) UNIQUE NOT NULL,
    Modelo NVARCHAR(100) NOT NULL,
    Año INT NOT NULL,
    Capacidad INT NOT NULL, -- Número de pasajeros
    Estado NVARCHAR(50) NOT NULL, -- Disponible, En Mantenimiento, En Uso
    FechaMantenimiento DATE,
    Combustible INT, -- Galones
    Kilometraje INT
);
```

#### ✈️ Tabla: Solicitudes
```sql
CREATE TABLE Solicitudes (
    SolicitudID INT PRIMARY KEY IDENTITY(1,1),
    EmpleadoID INT NOT NULL,
    VehiculoID INT,
    ConductorID INT,
    Destino NVARCHAR(200) NOT NULL,
    Motivo NVARCHAR(500),
    NumPasajeros INT NOT NULL,
    FechaSolicitud DATETIME NOT NULL DEFAULT GETDATE(),
    FechaViaje DATE NOT NULL,
    HoraViaje TIME,
    Estado NVARCHAR(50) NOT NULL, -- PENDIENTE, APROBADO, RECHAZADO
    Observaciones NVARCHAR(500),
    FOREIGN KEY (EmpleadoID) REFERENCES Empleados(EmpleadoID),
    FOREIGN KEY (VehiculoID) REFERENCES Vehículos(VehiculoID),
    FOREIGN KEY (ConductorID) REFERENCES Empleados(EmpleadoID)
);
```

### Validaciones Automáticas (Triggers)

El sistema incluye triggers para:
- ✅ Validar que solo operarios con licencia vigente soliciten viajes
- ✅ Evitar que admins y gerentes soliciten viajes directamente
- ✅ Verificar que el vehículo tenga capacidad suficiente
- ✅ Actualizar automáticamente el estado del vehículo

---

## 👥 Roles y Permisos

| Permiso | Administrador | Gerente | Operario |
|---|:---:|:---:|:---:|
| Ver empleados | ✅ | ❌ | ❌ |
| Crear empleado | ✅ | ❌ | ❌ |
| Editar empleado | ✅ | ❌ | ❌ |
| Ver vehículos | ✅ | ✅ | ❌ |
| Crear vehículo | ✅ | ❌ | ❌ |
| Solicitar viaje | ❌ | ❌ | ✅ |
| Aprobar solicitud | ✅ | ✅ | ❌ |
| Rechazar solicitud | ✅ | ✅ | ❌ |
| Ver reportes | ✅ | ✅ | ❌ |
| Ver mi historial | ✅ | ✅ | ✅ |

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

3. **Verifica las credenciales en `Conexion.java`:**
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
- ✅ Sistema de solicitudes de viaje
- ✅ Validación de roles y permisos
- ✅ Reportes en tiempo real
- ✅ Triggers automáticos en BD

---

**Última actualización:** 6 de mayo de 2026  
**Estado del Proyecto:** En Desarrollo Activo 🚀

