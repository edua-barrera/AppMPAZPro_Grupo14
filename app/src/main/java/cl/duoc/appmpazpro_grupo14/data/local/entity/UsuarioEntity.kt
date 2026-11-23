package cl.duoc.appmpazpro_grupo14.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val clave: String,
    val direccion: String,
    val aceptaTerminos: Boolean,
    val fotoUri: String? = null
)