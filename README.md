# ✈️ Sistema de Pasajes de Avión

Sistema de gestión y venta de pasajes de avión desarrollado en Java como proyecto final de la materia Programación.

## 📋 Descripción

Aplicación de escritorio desarrollada en Java que permite gestionar el proceso de reserva y venta de pasajes de avión.

El sistema cuenta con diferentes funcionalidades para pasajeros y administración, permitiendo gestionar usuarios, vuelos, reservas y pagos, además de generar los tickets de los pasajes en formato PDF.

## 🚀 Funcionalidades

### 👤 Usuarios
- Registro de usuarios.
- Inicio de sesión.
- Recuperación de contraseña.
- Gestión de datos del pasajero.

### ✈️ Vuelos
- Consulta de vuelos disponibles.
- Búsqueda de vuelos.
- Visualización del estado de los vuelos.
- Gestión de vuelos desde el área administrativa.

### 💺 Reservas
- Selección de vuelo.
- Selección de asientos.
- Gestión de reservas.
- Consulta del historial de reservas.
- Cancelación/gestión de reservas.

### 💳 Pagos
- Selección del medio de pago.
- Carga de datos de tarjeta.
- Confirmación de la operación.

### 🎫 Generación de tickets
- Generación automática del ticket después de confirmar la compra.
- Generación de archivos PDF.
- Visualización de los datos correspondientes al pasajero, vuelo y reserva.

## 🛠️ Tecnologías utilizadas

- **Java**
- **Java Swing** — Interfaz gráfica
- **MySQL** — Base de datos
- **JDBC** — Conexión con la base de datos
- **iTextPDF** — Generación de tickets en PDF
- **NetBeans** — Entorno de desarrollo
- **Git / GitHub** — Control de versiones

## 🏗️ Arquitectura

El proyecto se encuentra organizado en diferentes paquetes según la responsabilidad de cada componente:

```text
src/
├── Controlador/
├── Modelo/
├── Util/
├── Vista/
└── sistemapasajes/
