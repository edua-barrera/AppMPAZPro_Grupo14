package cl.duoc.appmpazpro_grupo14.data.local.dao

import androidx.room.*
import cl.duoc.appmpazpro_grupo14.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios ORDER BY id DESC LIMIT 1")
    fun obtenerUsuarioActual(): Flow<UsuarioEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: UsuarioEntity): Long

    @Update
    suspend fun actualizarUsuario(usuario: UsuarioEntity)

    @Query("UPDATE usuarios SET fotoUri = :uri WHERE id = :id")
    suspend fun actualizarFoto(id: Int, uri: String?)

    @Delete
    suspend fun eliminarUsuario(usuario: UsuarioEntity)
}