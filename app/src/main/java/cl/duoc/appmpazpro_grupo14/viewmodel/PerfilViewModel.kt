package cl.duoc.appmpazpro_grupo14.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfilViewModel : ViewModel() {
    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    fun actualizarImagen(uri: Uri?) {
        _imagenUri.value = uri
    }
}