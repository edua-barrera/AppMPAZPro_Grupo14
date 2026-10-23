package cl.duoc.appmpazpro_grupo14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.appmpazpro_grupo14.data.EstadoDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EstadoViewModel(application: Application) : AndroidViewModel(application) {
    private val estadoDataStore = EstadoDataStore(application)

    private val _activo = MutableStateFlow<Boolean?>(null)
    val activo: StateFlow<Boolean?> = _activo.asStateFlow()

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje.asStateFlow()

    init { cargarEstado() }

    private fun cargarEstado() {
        viewModelScope.launch {
            delay(1500) // Simulación de carga (loader)
            _activo.value = estadoDataStore.obtenerEstado().first() ?: false
        }
    }

    fun alternarEstado() {
        viewModelScope.launch {
            val nuevoValor = !(_activo.value ?: false)
            estadoDataStore.guardarEstado(nuevoValor)
            _activo.value = nuevoValor

            _mostrarMensaje.value = true
            delay(2000)
            _mostrarMensaje.value = false
        }
    }
}