# MPAZ PRO 3.º — Plataforma Didáctica de Aprendizaje

**Aplicación Móvil para la Escuela Escritora Marcela Paz (San Bernardo)**  
*Asignatura:* Desarrollo de Aplicaciones Móviles (DSY1105)  
*Carrera:* Ingeniería en Informática — Duoc UC

---

## 📌 Contexto del Proyecto

La Escuela Escritora Marcela Paz atiende a cerca de 270 estudiantes en San Bernardo. El proyecto surge ante la necesidad de superar las limitaciones del material impreso en 3.º Básico, permitiendo registrar formativamente el progreso, identificar fortalezas y apoyar ritmos de aprendizaje diferenciados en torno a Objetivos de Aprendizaje (OA) oficiales del Currículum Nacional.

Este prototipo (MVP) abarca una unidad curricular por cada una de las 4 asignaturas troncales del nivel, incorporando desafíos didácticos interactivos con retroalimentación inmediata y un Modo Repaso adaptativo.

---

## 🚀 Arquitectura y Patrones de Diseño

El proyecto sigue una arquitectura **MVVM (Model - View - ViewModel)** desacoplada y reactiva

* **UI (Jetpack Compose / Material 3):** Vistas declarativas que observan estados inmutables sin lógica de negocio.
* **ViewModels (`viewmodel/`):** 4 ViewModels independientes con Responsabilidad Única (SRP):
    * `MainViewModel`: Manejo desacoplado de eventos de navegación (`NavigationEvent`).
    * `UsuarioViewModel`: Validación de datos de registro escolar y formulario.
    * `EstadoViewModel`: Control del estado reactivo y persistencia del ritmo de aprendizaje.
    * `PerfilViewModel`: Gestión del flujo y estado de la imagen de credencial.
* **Data / Persistencia (`data/`):** `DataStore Preferences` para almacenamiento asíncrono y transaccional mediante Kotlin Coroutines y Flow.
* **Recursos Nativos:** Integración de Cámara y Galería mediante `ActivityResultContracts` (`TakePicture` y `GetContent`), asegurados con `FileProvider` (`content://`) y manejo de permisos en tiempo de ejecución (`RequestPermission`).

---

## 📚 Estructura Curricular del MVP (3.º Básico)

1. **Lenguaje y Comunicación:**
    * *Unidad 1:* Mundos de Fábula y Narración (OA 4 y OA 12).
2. **Matemática:**
    * *Unidad 1:* Números y Operaciones en la Escuela (OA 1 y OA 8).
3. **Ciencias Naturales:**
    * *Unidad 1:* Las Plantas y el Entorno Escolar (OA 1 y OA 3).
4. **Historia, Geografía y Ciencias Sociales:**
    * *Unidad 1:* Pueblos Originarios y Paisajes de Chile (OA 1 y OA 3).

---

## 💡 Modo Repaso Guiado (Adaptabilidad Pedagógica)

A través de `DataStore Preferences`, el sistema recuerda la preferencia de estudio del alumno:
* **Modo Estándar:** Desafíos formativos con evaluación formativa directa.
* **Modo Repaso Activo:** Inyección reactiva en tiempo real de pistas conceptuales y apoyos visuales en las preguntas de las 4 asignaturas, nivelando el aprendizaje de forma inclusiva.

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Kotlin 2.x
* **UI Framework:** Jetpack Compose (Material Design 3)
* **SDK Mínimo:** API 33 (Android 13.0)
* **Build System:** Gradle Kotlin DSL
* **Librerías principales:**
    * `androidx.navigation:navigation-compose`
    * `androidx.lifecycle:lifecycle-viewmodel-compose`
    * `androidx.datastore:datastore-preferences`
    * `io.coil-kt:coil-compose`
    * `androidx.compose.material:material-icons-extended`

---

## 👥 Datos Académicos y Autoría

* **Institución:** Duoc UC — Sede San Bernardo
* **Carrera:** Ingeniería en Informática
* **Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)
* **Proyecto:** Evaluación Sumativa 2 (AppMPAZPro_Grupo14)
* **Estudiante:** Eduardo Ignacio Barrera Ramos
* **Establecimiento de Aplicación:** Escuela Escritora Marcela Paz (San Bernardo)

---

## 📋 Trazabilidad de Versiones y Rúbrica (IE 2.3.2)

El desarrollo del proyecto se gestionó bajo Git/GitHub asegurando un historial limpio y estructurado:

1. **Commit Base Obligatorio:** `"Funcionalidad cámara y galería integrada"`.
2. **Refactor Visual Material 3:** Transición completa a paleta institucional y diseño responsivo para primer ciclo básico.
3. **Gestión de Permisos Nativos:** Integración de `RequestPermission` en tiempo de ejecución para cámara y galería (`FileProvider`).
4. **Conexión Pedagógica DataStore:** Implementación de persistencia asíncrona para el `Modo Repaso`, inyectando pistas formativas dinámicas por cada Objetivo de Aprendizaje (OA).

---

## ⚙️ Instrucciones de Instalación y Ejecución

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/CoKayn/AppMPAZPro_Grupo14.git](https://github.com/CoKayn/AppMPAZPro_Grupo14.git)