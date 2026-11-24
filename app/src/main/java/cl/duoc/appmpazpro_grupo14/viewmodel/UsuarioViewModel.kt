package cl.duoc.appmpazpro_grupo14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.appmpazpro_grupo14.data.local.AppDatabase
import cl.duoc.appmpazpro_grupo14.data.local.entity.ProgresoDesafioEntity
import cl.duoc.appmpazpro_grupo14.data.local.entity.UsuarioEntity
import cl.duoc.appmpazpro_grupo14.data.remote.AvanceRemotoDto
import cl.duoc.appmpazpro_grupo14.data.remote.BackendClient
import cl.duoc.appmpazpro_grupo14.model.UsuarioErrores
import cl.duoc.appmpazpro_grupo14.model.UsuarioUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val usuarioDao = database.usuarioDao()
    private val progresoDao = database.progresoDesafioDao()

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow()

    private val _sesionActiva = MutableStateFlow(false)
    val sesionActiva: StateFlow<Boolean> = _sesionActiva.asStateFlow()

    private val _errorLogin = MutableStateFlow<String?>(null)
    val errorLogin: StateFlow<String?> = _errorLogin.asStateFlow()

    private var usuarioIdActual: Int = 0
    private var usuarioEnBaseDatos: UsuarioEntity? = null

    val historialProgreso: StateFlow<List<ProgresoDesafioEntity>> =
        progresoDao.obtenerTodosLosProgresos()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _avancesRemotos = MutableStateFlow<List<AvanceRemotoDto>>(emptyList())
    val avancesRemotos: StateFlow<List<AvanceRemotoDto>> = _avancesRemotos.asStateFlow()

    private val _estadoServidor = MutableStateFlow("Listo para sincronizar con UTP")
    val estadoServidor: StateFlow<String> = _estadoServidor.asStateFlow()

    private val _modoDocenteUtp = MutableStateFlow(false)
    val modoDocenteUtp: StateFlow<Boolean> = _modoDocenteUtp.asStateFlow()

    init {
        cargarUsuarioDesdeSQLite()
        sincronizarDesdeServidor()
    }

    fun alternarModoDocente(activado: Boolean) {
        _modoDocenteUtp.value = activado
        if (activado) sincronizarDesdeServidor()
    }

    private fun cargarUsuarioDesdeSQLite() {
        viewModelScope.launch {
            usuarioDao.obtenerUsuarioActual().collectLatest { usuarioGuardado ->
                usuarioEnBaseDatos = usuarioGuardado
                if (usuarioGuardado != null) {
                    usuarioIdActual = usuarioGuardado.id
                    _sesionActiva.value = true
                    _estado.update {
                        it.copy(
                            nombre = usuarioGuardado.nombre,
                            correo = usuarioGuardado.correo,
                            clave = usuarioGuardado.clave,
                            direccion = usuarioGuardado.direccion,
                            aceptaTerminos = usuarioGuardado.aceptaTerminos
                        )
                    }
                }
            }
        }
    }

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }
    fun onCorreoChange(valor: String) {
        _errorLogin.value = null
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }
    fun onClaveChange(valor: String) {
        _errorLogin.value = null
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }
    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }
    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun iniciarSesion(correoInput: String, claveInput: String): Boolean {
        val usuario = usuarioEnBaseDatos
        if (usuario == null) {
            _errorLogin.value = "No hay cuentas registradas. Crea una en la pestaña Registrarse."
            return false
        }
        return if (usuario.correo.equals(correoInput.trim(), ignoreCase = true) && usuario.clave == claveInput) {
            usuarioIdActual = usuario.id
            _estado.update {
                it.copy(
                    nombre = usuario.nombre,
                    correo = usuario.correo,
                    clave = usuario.clave,
                    direccion = usuario.direccion,
                    aceptaTerminos = usuario.aceptaTerminos
                )
            }
            _sesionActiva.value = true
            _errorLogin.value = null
            true
        } else {
            _errorLogin.value = "Correo o clave incorrectos."
            false
        }
    }

    fun cerrarSesion() {
        _sesionActiva.value = false
        _modoDocenteUtp.value = false
        _errorLogin.value = null
        _estado.value = UsuarioUiState()
    }

    fun validarYGuardarUsuario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6) "Mínimo 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null
        )

        val hayErrores = listOfNotNull(errores.nombre, errores.correo, errores.clave, errores.direccion).isNotEmpty()
        _estado.update { it.copy(errores = errores) }

        if (!hayErrores) {
            viewModelScope.launch {
                val entidad = UsuarioEntity(
                    id = usuarioIdActual,
                    nombre = estadoActual.nombre.trim(),
                    correo = estadoActual.correo.trim(),
                    clave = estadoActual.clave,
                    direccion = estadoActual.direccion.trim(),
                    aceptaTerminos = estadoActual.aceptaTerminos
                )
                val nuevoId = usuarioDao.insertarUsuario(entidad)
                if (usuarioIdActual == 0) {
                    usuarioIdActual = nuevoId.toInt()
                }
                _sesionActiva.value = true
            }
        }

        return !hayErrores
    }

    fun registrarProgreso(
        asignatura: String,
        codigoOa: String,
        acertado: Boolean,
        usoModoRepaso: Boolean,
        pokemonRecompensa: String = "",
        observacionDocente: String = "Completado en plataforma"
    ) {
        viewModelScope.launch {
            progresoDao.insertarProgreso(
                ProgresoDesafioEntity(
                    asignatura = asignatura,
                    codigoOa = codigoOa,
                    acertado = acertado,
                    usoModoRepaso = usoModoRepaso,
                    pokemonRecompensa = pokemonRecompensa,
                    observacionDocente = observacionDocente
                )
            )
            val estudianteNombre = _estado.value.nombre.ifBlank { "Estudiante 3.º Básico" }
            try {
                BackendClient.api.crearAvance(
                    AvanceRemotoDto(
                        estudiante = estudianteNombre,
                        asignatura = asignatura,
                        codigoOa = codigoOa,
                        acertado = acertado,
                        pokemonRecompensa = pokemonRecompensa.ifBlank { "Sin recompensa" },
                        observacionDocente = observacionDocente,
                        validadoPorDocente = false
                    )
                )
                sincronizarDesdeServidor()
            } catch (_: Exception) {
                _estadoServidor.value = "Guardado en SQLite (Servidor UTP desconectado)"
            }
        }
    }

    fun actualizarObservacionProgreso(item: ProgresoDesafioEntity, nuevaObservacion: String) {
        viewModelScope.launch {
            progresoDao.actualizarProgreso(item.copy(observacionDocente = nuevaObservacion))
        }
    }

    fun eliminarProgreso(item: ProgresoDesafioEntity) {
        viewModelScope.launch {
            progresoDao.eliminarProgreso(item)
        }
    }

    fun sincronizarDesdeServidor() {
        viewModelScope.launch {
            _estadoServidor.value = "Consultando servidor UTP..."
            try {
                val lista = BackendClient.api.obtenerAvances()
                _avancesRemotos.value = lista
                _estadoServidor.value = "Conectado (${lista.size} registros en Spring Boot)"
            } catch (e: Exception) {
                _estadoServidor.value = "Sin conexión al backend (puerto 8080)"
            }
        }
    }

    fun crearAvanceEnServidor(
        asignatura: String,
        codigoOa: String,
        observacion: String,
        acertado: Boolean,
        validado: Boolean
    ) {
        viewModelScope.launch {
            try {
                val estudiante = _estado.value.nombre.ifBlank { "Eduardo Barrera" }
                BackendClient.api.crearAvance(
                    AvanceRemotoDto(
                        estudiante = estudiante,
                        asignatura = asignatura,
                        codigoOa = codigoOa,
                        acertado = acertado,
                        pokemonRecompensa = "Registro UTP",
                        observacionDocente = observacion,
                        validadoPorDocente = validado
                    )
                )
                sincronizarDesdeServidor()
            } catch (e: Exception) {
                _estadoServidor.value = "Error POST: ${e.message}"
            }
        }
    }

    fun actualizarAvanceEnServidor(avance: AvanceRemotoDto, nuevaObs: String, validado: Boolean) {
        val id = avance.id ?: return
        viewModelScope.launch {
            try {
                BackendClient.api.actualizarAvance(
                    id,
                    avance.copy(
                        observacionDocente = nuevaObs,
                        validadoPorDocente = validado
                    )
                )
                sincronizarDesdeServidor()
            } catch (e: Exception) {
                _estadoServidor.value = "Error PUT: ${e.message}"
            }
        }
    }

    fun eliminarAvanceEnServidor(id: Long?) {
        if (id == null) return
        viewModelScope.launch {
            try {
                BackendClient.api.eliminarAvance(id)
                sincronizarDesdeServidor()
            } catch (e: Exception) {
                _estadoServidor.value = "Error DELETE: ${e.message}"
            }
        }
    }
}