# ✈️ Sistema de Pasajes de Avión

Sistema de gestión y venta de pasajes de avión desarrollado en Java como proyecto final de la materia Programación.

## 📋 Descripción

Aplicación de escritorio que permite gestionar el proceso de compra y reserva de pasajes de avión.

El sistema permite a los usuarios registrarse e iniciar sesión, consultar vuelos, seleccionar asientos, realizar reservas y gestionar el proceso de pago. Una vez confirmada una reserva, el sistema genera el ticket correspondiente en formato PDF.

Además, cuenta con funcionalidades destinadas a la gestión de usuarios, vuelos y reservas.

## 🚀 Funcionalidades

### 👤 Gestión de usuarios

- Registro de nuevos usuarios.
- Inicio de sesión.
- Recuperación de contraseña.
- Gestión de sesión del usuario.
- Gestión de datos del pasajero.

### ✈️ Gestión de vuelos

- Consulta y búsqueda de vuelos.
- Visualización de información de los vuelos.
- Selección de vuelos disponibles.
- Gestión de vuelos desde el área administrativa.
- Visualización del estado de los vuelos.

### 💺 Reservas y asientos

- Selección de vuelo.
- Selección de asientos.
- Reserva de pasajes.
- Gestión de reservas.
- Consulta del historial de reservas.

### 💳 Pagos

- Selección del método de pago.
- Ingreso de los datos correspondientes.
- Confirmación del pago.
- Asociación del pago con la reserva.

### 🎫 Tickets y check-in

- Generación del ticket del pasaje en formato PDF.
- Generación del comprobante de check-in en PDF.
- Descarga de los documentos generados.
- Los archivos se generan automáticamente al completar las operaciones correspondientes.

### 🛠️ Administración

- Gestión de usuarios.
- Gestión de vuelos.
- Gestión de reservas.
- Consulta y administración del estado de los vuelos.

## 🛠️ Tecnologías utilizadas

- **Java**
- **Java Swing** — Desarrollo de la interfaz gráfica.
- **MySQL** — Gestión de la base de datos.
- **JDBC** — Conexión con la base de datos.
- **iTextPDF 5.5.13** — Generación de documentos PDF.
- **NetBeans** — Entorno de desarrollo.
- **Git / GitHub** — Control de versiones.

## 🏗️ Organización del proyecto

El código fuente se encuentra organizado en diferentes paquetes según la responsabilidad de cada componente:

### Controlador

Contiene las clases encargadas de gestionar las acciones y la lógica relacionada con usuarios, vuelos y reservas.

### Modelo

Contiene las clases principales del sistema y las relacionadas con el acceso a datos, incluyendo usuarios, pasajeros, vuelos, reservas y pagos.

### Util

Incluye clases auxiliares para funcionalidades como la generación de documentos PDF y la gestión de reservas.

### Vista

Contiene las interfaces gráficas desarrolladas con Java Swing para las diferentes funcionalidades del sistema.

## 📁 Estructura general

```text
SistemaPasajes/
├── src/
│   ├── Controlador/
│   ├── Modelo/
│   ├── Util/
│   ├── Vista/
│   └── sistemapasajes/
├── nbproject/
├── lib/
├── test/
├── tickets_generados/
├── .gitignore
├── build.xml
└── manifest.mf
```

La carpeta `tickets_generados` se utiliza para almacenar temporalmente los documentos PDF generados durante la ejecución del sistema.

Los archivos PDF generados durante las pruebas no se incluyen en el repositorio gracias al archivo `.gitignore`.

## 🗄️ Base de datos

El sistema utiliza una base de datos para almacenar y gestionar la información relacionada con:

- Usuarios.
- Pasajeros.
- Vuelos.
- Reservas.
- Pagos.

La conexión con la base de datos se gestiona mediante JDBC a través de la clase `ConexionBD`.

## 🎯 Objetivo del proyecto

El objetivo fue desarrollar un sistema completo de venta y gestión de pasajes de avión, aplicando los conocimientos adquiridos durante la materia Programación.

Durante el desarrollo se trabajaron conceptos de:

- Programación Orientada a Objetos.
- Separación de responsabilidades.
- Interfaces gráficas.
- Manejo de eventos.
- Persistencia de datos.
- Conexión con bases de datos.
- Gestión de reservas.
- Generación de documentos PDF.

## 📸 Capturas de pantalla

### 🔐 Inicio de sesión

<!-- Agregar captura -->

### 📝 Registro de usuario

<!-- Agregar captura -->

### ✈️ Búsqueda y selección de vuelos

<!-- Agregar captura -->

### 💺 Selección de asientos

<!-- Agregar captura -->

### 💳 Pago

<!-- Agregar captura -->

### ✅ Confirmación de reserva

<!-- Agregar captura -->

### 🎫 Ticket generado

<!-- Agregar captura -->

## 👩‍💻 Proyecto académico

Proyecto desarrollado como trabajo final de la materia **Programación**.

**Tecnología principal:** Java
