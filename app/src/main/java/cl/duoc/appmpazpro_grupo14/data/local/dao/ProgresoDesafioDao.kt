package cl.duoc.appmpazpro_grupo14.data.local.dao

import androidx.room.*
import cl.duoc.appmpazpro_grupo14.data.local.entity.ProgresoDesafioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgresoDesafioDao {
    @Query("SELECT * FROM progreso_desafios ORDER BY id DESC")
    fun obtenerTodosLosProgresos(): Flow<List<ProgresoDesafioEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProgreso(progreso: ProgresoDesafioEntity)

    @Update
    suspend fun actualizarProgreso(progreso: ProgresoDesafioEntity)

    @Delete
    suspend fun eliminarProgreso(progreso: ProgresoDesafioEntity)

    @Query("DELETE FROM progreso_desafios")
    suspend fun limpiarHistorial()
}