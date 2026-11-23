package cl.duoc.appmpazpro_grupo14.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.duoc.appmpazpro_grupo14.data.local.dao.ProgresoDesafioDao
import cl.duoc.appmpazpro_grupo14.data.local.dao.UsuarioDao
import cl.duoc.appmpazpro_grupo14.data.local.entity.ProgresoDesafioEntity
import cl.duoc.appmpazpro_grupo14.data.local.entity.UsuarioEntity

@Database(
    entities = [UsuarioEntity::class, ProgresoDesafioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun progresoDesafioDao(): ProgresoDesafioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpaz_pro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}