package cl.duoc.appmpazpro_grupo14.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.appmpazpro_grupo14.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PerfilViewModel(application: Application) : AndroidViewModel(application) {
    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    private var usuarioId: Int? = null

    init {
        viewModelScope.launch {
            usuarioDao.obtenerUsuarioActual().collectLatest { usuario ->
                usuarioId = usuario?.id
                _imagenUri.value = usuario?.fotoUri?.let { Uri.parse(it) }
            }
        }
    }

    fun actualizarImagen(uri: Uri?) {
        _imagenUri.value = uri
        viewModelScope.launch {
            usuarioId?.let { id ->
                usuarioDao.actualizarFoto(id, uri?.toString())
            }
        }
    }
}