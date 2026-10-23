package cl.duoc.appmpazpro_grupo14.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "preferencias_mpaz")

class EstadoDataStore(private val context: Context) {
    private val MODO_REPASO = booleanPreferencesKey(name = "modo_repaso_activado")

    suspend fun guardarEstado(valor: Boolean) {
        context.dataStore.edit { preferencias ->
            preferencias[MODO_REPASO] = valor
        }
    }

    fun obtenerEstado(): Flow<Boolean?> {
        return context.dataStore.data.map { preferencias ->
            preferencias[MODO_REPASO]
        }
    }
}