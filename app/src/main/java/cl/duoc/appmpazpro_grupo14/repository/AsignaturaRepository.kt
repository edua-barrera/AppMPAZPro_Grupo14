package cl.duoc.appmpazpro_grupo14.repository

import cl.duoc.appmpazpro_grupo14.model.Asignatura

class AsignaturaRepository {
    fun obtenerAsignaturasMVP(): List<Asignatura> {
        return listOf(
            Asignatura(1, "Lenguaje y Comunicación", "Unidad 1: Comprensión Lectora", "OA 04: Leer comprensivamente fábulas y mitos."),
            Asignatura(2, "Matemática", "Unidad 1: Números y Operaciones", "OA 02: Representar y describir la adición hasta el 1000."),
            Asignatura(3, "Ciencias Naturales", "Unidad 1: El Cuerpo Humano", "OA 08: Reconocer la importancia de la actividad física."),
            Asignatura(4, "Historia, Geografía y CCSS", "Unidad 1: Nuestro Entorno", "OA 06: Ubicar en mapas las zonas climáticas de la Tierra.")
        )
    }
}