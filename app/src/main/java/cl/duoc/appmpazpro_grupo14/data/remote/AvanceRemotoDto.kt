package cl.duoc.appmpazpro_grupo14.data.remote

data class AvanceRemotoDto(
    val id: Long? = null,
    val estudiante: String,
    val asignatura: String,
    val codigoOa: String,
    val acertado: Boolean,
    val pokemonRecompensa: String,
    val observacionDocente: String,
    val validadoPorDocente: Boolean = false
)