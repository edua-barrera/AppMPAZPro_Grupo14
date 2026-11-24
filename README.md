# MPAZ PRO 3.º — Plataforma Didáctica de Aprendizaje

**Aplicación Móvil para la Escuela Escritora Marcela Paz (San Bernardo)**  
*Asignatura:* Desarrollo de Aplicaciones Móviles (DSY1105)  
*Carrera:* Ingeniería en Informática — Duoc UC

---

## 📌 Contexto del Proyecto

La Escuela Escritora Marcela Paz atiende a cerca de 270 estudiantes en San Bernardo. El proyecto surge ante la necesidad de superar las limitaciones del material impreso en 3.º Básico, permitiendo registrar formativamente el progreso, identificar fortalezas y apoyar ritmos de aprendizaje diferenciados en torno a Objetivos de Aprendizaje (OA) oficiales del Currículum Nacional.

La plataforma abarca unidades curriculares en las asignaturas troncales del nivel, incorporando desafíos didácticos interactivos con retroalimentación inmediata, un Modo Repaso adaptativo, recompensas gamificadas (Pokédex) y sincronización híbrida con un microservicio backend para gestión pedagógica de Jefatura UTP.

---

## 🚀 Arquitectura y Patrones de Diseño

El proyecto sigue una arquitectura **MVVM (Model - View - ViewModel)** desacoplada y reactiva:

- **UI (`ui/` — Jetpack Compose / Material 3):** Vistas declarativas que observan estados inmutables (`StateFlow`) sin lógica de negocio.
- **ViewModels (`viewmodel/`):**
    - `MainViewModel`: Manejo desacoplado de eventos de navegación (`NavigationEvent`).
    - `UsuarioViewModel`: Validación de registro/inicio de sesión escolar, gestión de bitácora local en SQLite, control de perfiles (**Estudiante 3.º Básico** vs. **Docente / Jefatura UTP**) y sincronización CRUD con el microservicio REST.
    - `EstadoViewModel`: Control del estado reactivo y persistencia del ritmo de aprendizaje (`Modo Repaso`).
    - `PerfilViewModel`: Gestión del flujo y estado de la fotografía de credencial escolar.
- **Persistencia Local (`data/local/`):**
    - **Room (SQLite):** Almacenamiento relacional offline para credenciales de usuario (`UsuarioEntity`) y bitácora de desafíos (`ProgresoDesafioEntity`).
    - **DataStore Preferences:** Almacenamiento asíncrono y transaccional mediante Kotlin Coroutines y Flow para preferencias pedagógicas.
- **Consumo de APIs Externas y Microservicio (`data/remote/`):**
    - **Retrofit + Gson:** Integración dual con **PokéAPI** (asignación dinámica de compañeros Pokédex como refuerzo positivo) y con el microservicio propio **Spring Boot (`mpaz-backend`)** mediante operaciones `GET`, `POST`, `PUT` y `DELETE` sobre el recurso `/api/avances`.
- **Recursos Nativos:** Integración de Cámara y Galería mediante `ActivityResultContracts` (`TakePicture` y `GetContent`), asegurados con `FileProvider` (`content://`) y manejo de permisos en tiempo de ejecución (`RequestPermission`).

---

## 📚 Estructura Curricular (3.º Básico)

1. **Lenguaje y Comunicación:**
    - *Unidad 1:* Mundos de Fábula y Narración (OA 4 y OA 12).
2. **Matemática:**
    - *Unidad 1:* Números y Operaciones en la Escuela (OA 1 y OA 8).
3. **Ciencias Naturales:**
    - *Unidad 1:* Las Plantas y el Entorno Escolar (OA 1 y OA 3).
4. **Historia, Geografía y Ciencias Sociales:**
    - *Unidad 1:* Pueblos Originarios y Paisajes de Chile (OA 1 y OA 3).

---

## 🔐 Control de Perfiles y Modo Repaso Guiado

- **Separación de Roles en Credencial (`ResumenScreen`):**
    - **Perfil Estudiante 3.º Básico:** Vista de lectura de credencial escolar, logros obtenidos y compañeros Pokédex desbloqueados.
    - **Perfil Docente / Jefatura UTP:** Habilita la edición/eliminación local en SQLite y el panel de **Informe Pedagógico UTP (Spring Boot)** para crear, validar profesionalmente y auditar avances de Objetivos de Aprendizaje en tiempo real.
- **Modo Repaso Guiado (`DataStore Preferences`):**
    - **Modo Estándar:** Desafíos formativos con evaluación directa.
    - **Modo Repaso Activo:** Inyección reactiva en tiempo real de pistas conceptuales y apoyos visuales en las preguntas, nivelando el aprendizaje de forma inclusiva.

---

## 🛠️ Stack Tecnológico

- **Lenguaje:** Kotlin 2.x
- **UI Framework:** Jetpack Compose (Material Design 3)
- **SDK Mínimo:** API 33 (Android 13.0)
- **Build System:** Gradle Kotlin DSL
- **Persistencia y Red:**
    - `androidx.room:room-runtime` & `room-ktx` (SQLite)
    - `androidx.datastore:datastore-preferences`
    - `com.squareup.retrofit2:retrofit` & `converter-gson`
    - `io.coil-kt:coil-compose`
- **Backend Complementario (`mpaz-backend`):** Java 17 / Spring Boot 3.3.5 / Spring Data JPA / Base de datos H2 (Puerto `8080`).
- **Testing y Distribución:** JUnit 4 (`MpazLogicaNegocioTest`), firma criptográfica Keystore (`.jks`) y empaquetado `app-release.apk`.

---

## 👥 Datos Académicos y Autoría

- **Institución:** Duoc UC — Sede San Bernardo
- **Carrera:** Ingeniería en Informática
- **Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)
- **Proyecto:** AppMPAZPro_Grupo14
- **Estudiante:** Eduardo Ignacio Barrera Ramos
- **Establecimiento de Aplicación:** Escuela Escritora Marcela Paz (San Bernardo)

---

## 📋 Trazabilidad de Versiones

1. **Base Estructural y Recursos Nativos:** Arquitectura MVVM, navegación Compose e integración de cámara y galería (`FileProvider`).
2. **Persistencia Híbrida Local:** Implementación de `DataStore Preferences` (Modo Repaso) y base de datos `Room` (SQLite) para usuarios y bitácora de desafíos.
3. **Integración de APIs y Microservicio REST:** Conexión con PokéAPI y desarrollo de microservicio Spring Boot (`mpaz-backend`) con control de acceso por perfil (Estudiante vs. Docente UTP).
4. **Calidad y Empaquetado Release:** Suite de pruebas unitarias (`MpazLogicaNegocioTest`) y generación de binario firmado `app-release.apk` mediante certificado Keystore (`.jks`).

---

## ⚙️ Instrucciones de Instalación, Pruebas y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/edua-barrera/AppMPAZPro_Grupo14.git](https://github.com/edua-barrera/AppMPAZPro_Grupo14.git)

2. **Levantar el microservicio Spring Boot (mpaz-backend)**
   ```bash
   cd mpaz-backend
   .\gradlew bootRun
   
3. **Vincular puerto local con el emulador Android (adb reverse)**
    ```bash
   adb reverse tcp:8080 tcp:8080
   
4. **Ejecutar pruebas unitarias:**
    ```bash
    .\gradlew testDebugUnitTest --tests "cl.duoc.appmpazpro_grupo14.MpazLogicaNegocioTest"
   
## 📦 Artefacto de Distribución Firmado:

- **Binario: app/build/outputs/apk/release/app-release.apk**
- **Alias de firma: key_grupo14 (Keystore .jks generado para distribución académica).**

