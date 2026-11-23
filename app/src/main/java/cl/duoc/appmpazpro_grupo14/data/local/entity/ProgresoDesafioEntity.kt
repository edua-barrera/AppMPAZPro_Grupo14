package cl.duoc.appmpazpro_grupo14.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progreso_desafios")
data class ProgresoDesafioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val asignatura: String,
    val codigoOa: String,
    val acertado: Boolean,
    val usoModoRepaso: Boolean,
    val pokemonRecompensa: String = "",
    val observacionDocente: String = "Completado en plataforma"
)