package cl.duoc.appmpazpro_grupo14

import cl.duoc.appmpazpro_grupo14.data.remote.AvanceRemotoDto
import cl.duoc.appmpazpro_grupo14.model.UsuarioErrores
import cl.duoc.appmpazpro_grupo14.model.UsuarioUiState
import org.junit.Assert.*
import org.junit.Test

class MpazLogicaNegocioTest {

    @Test
    fun `validacion detecta errores cuando los campos estan vacios o invalidos`() {
        val estadoInvalido = UsuarioUiState(
            nombre = "",
            correo = "correo-sin-arroba",
            clave = "123",
            direccion = ""
        )

        val errores = UsuarioErrores(
            nombre = if (estadoInvalido.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoInvalido.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoInvalido.clave.length < 6) "Mínimo 6 caracteres" else null,
            direccion = if (estadoInvalido.direccion.isBlank()) "Campo obligatorio" else null
        )

        assertNotNull(errores.nombre)
        assertNotNull(errores.correo)
        assertNotNull(errores.clave)
        assertNotNull(errores.direccion)
    }

    @Test
    fun `validacion aprueba datos correctos de estudiante de tercero basico`() {
        val estadoValido = UsuarioUiState(
            nombre = "Eduardo Barrera",
            correo = "apoderado@escuelampaz.cl",
            clave = "clave123",
            direccion = "San Bernardo",
            aceptaTerminos = true
        )

        val errores = UsuarioErrores(
            nombre = if (estadoValido.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoValido.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoValido.clave.length < 6) "Mínimo 6 caracteres" else null,
            direccion = if (estadoValido.direccion.isBlank()) "Campo obligatorio" else null
        )

        val hayErrores = listOfNotNull(errores.nombre, errores.correo, errores.clave, errores.direccion).isNotEmpty()
        assertFalse(hayErrores)
        assertTrue(estadoValido.aceptaTerminos)
    }

    @Test
    fun `dto de avance remoto conserva estado de validacion UTP y recompensa`() {
        val avance = AvanceRemotoDto(
            id = 1L,
            estudiante = "Eduardo Barrera",
            asignatura = "Matemática",
            codigoOa = "OA 8",
            acertado = true,
            pokemonRecompensa = "#025 Pikachu",
            observacionDocente = "Logrado sin errores",
            validadoPorDocente = false
        )

        val avanceValidado = avance.copy(
            observacionDocente = "Aprobado por Jefatura UTP",
            validadoPorDocente = true
        )

        assertEquals(1L, avanceValidado.id)
        assertEquals("OA 8", avanceValidado.codigoOa)
        assertTrue(avanceValidado.validadoPorDocente)
        assertEquals("Aprobado por Jefatura UTP", avanceValidado.observacionDocente)
    }

    @Test
    fun `respuesta incorrecta en desafio activa recomendacion de modo repaso`() {
        val respuestaAcertada = false
        val activarModoRepaso = !respuestaAcertada
        val observacionGenerada = if (respuestaAcertada) {
            "Logrado con recompensa Pokédex"
        } else {
            "Requiere refuerzo en Modo Repaso"
        }

        assertTrue(activarModoRepaso)
        assertEquals("Requiere refuerzo en Modo Repaso", observacionGenerada)
    }
}