# 🚗 Sistema de Gestión Vehicular y Solicitudes de Viaje

Este proyecto es una aplicación de escritorio desarrollada en **Java** diseñada para optimizar el control de flota vehicular, asignación de conductores y procesamiento de solicitudes de transporte dentro de una organización.

## 📌 Descripción del Proyecto

El sistema permite centralizar la información de los vehículos disponibles y gestionar el ciclo de vida de una solicitud de viaje, desde su creación hasta su aprobación. Incluye validaciones de seguridad, como la verificación de licencias de conducir y disponibilidad de unidades, asegurando un control eficiente de los recursos.

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje de Programación:** Java (JDK 17+)
*   **Interfaz Gráfica:** Java Swing / AWT
*   **Gestor de Base de Datos:** SQL Server
*   **Entorno de Desarrollo:** NetBeans IDE
*   **Control de Versiones:** Git / GitHub

## 📂 Estructura de la Base de Datos

El sistema se apoya en la base de datos `BD_GestionVehicular`, la cual incluye:
*   **Tablas:** Empleados, Vehículos, Solicitudes.
*   **Lógica de Negocio:** Implementación de `Triggers` para validaciones automáticas y `Seeders` para la carga inicial de datos.
*   **Restricciones:** Los administradores y gerentes están restringidos de solicitar viajes directamente, priorizando el uso para personal operativo con licencia vigente.

## 🚀 Funcionalidades Principales

*   **Mantenimiento de Catálogos:** Gestión completa de empleados y vehículos.
*   **Gestión de Viajes:** Formulario dinámico para registrar destinos, motivos de viaje y número de pasajeros (`txtDestino`, `tblSolicitudes`).
*   **Validación de Roles:** Control de acceso y permisos basado en el tipo de empleado.
*   **Reporte de Solicitudes:** Visualización en tiempo real del estado de las peticiones (PENDIENTE, APROBADO, RECHAZADO).

## ⚙️ Configuración e Instalación

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/ivonne06/Proyecto_GestionVehicular.git](https://github.com/ivonne06/Proyecto_GestionVehicular.git)
    ```
2.  **Base de Datos:**
    *   Importar el script SQL proporcionado en el servidor de SQL Server.
    *   Asegurar que el nombre de la base de datos sea `BD_GestionVehicular`.
3.  **Conexión:**
    *   Configurar las credenciales de acceso (usuario y contraseña) en la clase de conexión dentro del paquete de modelos en NetBeans.
4.  **Ejecución:**
    *   Abrir el proyecto en NetBeans, realizar un `Clean and Build` y ejecutar la clase principal.

---

## 👤 Desarrolladores

*   **Nombres:**
*   Ivonne Delgado
*   Himer Gonzalez
*   Jose Segura
*   **Institución:** Universidad Tecnológica de El Salvador (UTEC)
*   **Carrera:** Ingeniería en Sistemas y Computación
