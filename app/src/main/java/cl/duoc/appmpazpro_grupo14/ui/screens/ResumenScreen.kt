package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.appmpazpro_grupo14.data.local.entity.ProgresoDesafioEntity
import cl.duoc.appmpazpro_grupo14.data.remote.AvanceRemotoDto
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    usuarioViewModel: UsuarioViewModel,
    mainViewModel: MainViewModel
) {
    val estado by usuarioViewModel.estado.collectAsState()
    val historial by usuarioViewModel.historialProgreso.collectAsState()
    val avancesRemotos by usuarioViewModel.avancesRemotos.collectAsState()
    val estadoServidor by usuarioViewModel.estadoServidor.collectAsState()
    val modoDocente by usuarioViewModel.modoDocenteUtp.collectAsState()

    var itemAEditar by remember { mutableStateOf<ProgresoDesafioEntity?>(null) }
    var textoObservacion by remember { mutableStateOf("") }

    var remotoAEditar by remember { mutableStateOf<AvanceRemotoDto?>(null) }
    var textoObsRemota by remember { mutableStateOf("") }
    var validadoRemoto by remember { mutableStateOf(false) }

    var mostrarDialogoAgregar by remember { mutableStateOf(false) }
    val opcionesPredefinidas = listOf(
        "Lenguaje y Comunicación" to "OA 4",
        "Matemática" to "OA 8",
        "Ciencias Naturales" to "OA 1",
        "Historia y Geografía" to "OA 3",
        "Inglés" to "OA 1"
    )
    var asignaturaInput by remember { mutableStateOf("Lenguaje y Comunicación") }
    var oaInput by remember { mutableStateOf("OA 4") }
    var observacionInput by remember { mutableStateOf("Completado en plataforma") }
    var estadoLogradoInput by remember { mutableStateOf(true) }
    var validadoDocenteInput by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Credencial y Avance Escolar", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFEBF3FA), Color(0xFFF8FAFC))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("ESCUELA ESCRITORA MARCELA PAZ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text("San Bernardo • Tercero Básico", color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp)
                                }
                                Text("🎓", fontSize = 24.sp)
                            }
                        }

                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Estudiante:", color = Color.Gray, fontSize = 12.sp)
                            Text(estado.nombre.ifBlank { "Estudiante 3.º Básico" }, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Text("Contacto Apoderado:", color = Color.Gray, fontSize = 12.sp)
                            Text(estado.correo.ifBlank { "Sin correo registrado" }, fontWeight = FontWeight.Medium, fontSize = 14.sp)

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Text("Comuna / Residencia:", color = Color.Gray, fontSize = 12.sp)
                            Text(estado.direccion.ifBlank { "San Bernardo, Chile" }, fontWeight = FontWeight.Medium, fontSize = 14.sp)

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Text("Compromiso Pedagógico:", color = Color.Gray, fontSize = 12.sp)
                            Text(
                                if (estado.aceptaTerminos) "✅ Aceptado formalmente (SQLite)" else "❌ Pendiente de aceptación",
                                color = if (estado.aceptaTerminos) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = if (modoDocente) Color(0xFFE8F5E9) else Color(0xFFE3F2FD)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (modoDocente) "Perfil: Docente / Jefatura UTP" else "Perfil: Estudiante 3.º Básico",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (modoDocente) Color(0xFF1B5E20) else Color(0xFF0D47A1)
                            )
                            Text(
                                text = if (modoDocente)
                                    "Edición y validación de informe pedagógico habilitada."
                                else
                                    "Vista estudiante: lectura de logros y compañeros Pokédex.",
                                fontSize = 11.sp,
                                color = Color(0xFF334155)
                            )
                        }
                        Switch(
                            checked = modoDocente,
                            onCheckedChange = { usuarioViewModel.alternarModoDocente(it) }
                        )
                    }
                }

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Bitácora de Desafíos (SQLite)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF1E293B)
                            )
                            if (modoDocente) {
                                FilledTonalIconButton(
                                    onClick = {
                                        asignaturaInput = "Lenguaje y Comunicación"
                                        oaInput = "OA 4"
                                        observacionInput = "Completado en plataforma"
                                        estadoLogradoInput = true
                                        validadoDocenteInput = true
                                        mostrarDialogoAgregar = true
                                    }
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Agregar registro a bitácora")
                                }
                            }
                        }

                        if (historial.isEmpty()) {
                            Text(
                                text = "Aún no hay desafíos registrados en la base de datos local. Completa un desafío en Asignaturas para ganar tu primer Pokémon.",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        } else {
                            historial.forEach { item ->
                                HorizontalDivider(color = Color(0xFFE2E8F0))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${item.asignatura} (${item.codigoOa}) • ${if (item.acertado) "Logrado ✅" else "En práctica 🔄"}",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 13.sp
                                        )
                                        if (item.pokemonRecompensa.isNotBlank()) {
                                            Text(
                                                text = "Compañero Pokédex: ${item.pokemonRecompensa}",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        if (modoDocente) {
                                            Text(
                                                text = "Obs: ${item.observacionDocente}",
                                                fontSize = 12.sp,
                                                color = Color(0xFF475569)
                                            )
                                        }
                                    }
                                    if (modoDocente) {
                                        Row {
                                            IconButton(onClick = {
                                                itemAEditar = item
                                                textoObservacion = item.observacionDocente
                                            }) {
                                                Icon(Icons.Default.Edit, contentDescription = "Editar observación", tint = MaterialTheme.colorScheme.primary)
                                            }
                                            IconButton(onClick = {
                                                usuarioViewModel.eliminarProgreso(item)
                                            }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Eliminar registro", tint = MaterialTheme.colorScheme.error)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (modoDocente) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Informe Pedagógico UTP (Spring Boot)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color(0xFF1B5E20)
                                    )
                                    Text(
                                        text = estadoServidor,
                                        fontSize = 12.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                                Row {
                                    IconButton(onClick = { usuarioViewModel.sincronizarDesdeServidor() }) {
                                        Icon(Icons.Default.Refresh, contentDescription = "Sincronizar GET")
                                    }
                                    FilledTonalIconButton(
                                        onClick = {
                                            asignaturaInput = "Matemática"
                                            oaInput = "OA 8"
                                            observacionInput = "Validado por UTP"
                                            estadoLogradoInput = true
                                            validadoDocenteInput = true
                                            mostrarDialogoAgregar = true
                                        }
                                    ) {
                                        Icon(Icons.Default.CloudUpload, contentDescription = "Crear en servidor POST")
                                    }
                                }
                            }

                            if (avancesRemotos.isEmpty()) {
                                Text(
                                    text = "Sin registros remotos o servidor sin conexión en puerto 8080.",
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B)
                                )
                            } else {
                                avancesRemotos.forEach { remoto ->
                                    HorizontalDivider(color = Color(0xFFE2E8F0))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "#${remoto.id} • ${remoto.asignatura} (${remoto.codigoOa})",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp
                                            )
                                            Text(
                                                text = "Alumno: ${remoto.estudiante} • ${remoto.pokemonRecompensa}",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "Obs: ${remoto.observacionDocente}",
                                                fontSize = 12.sp,
                                                color = Color(0xFF475569)
                                            )
                                            Text(
                                                text = if (remoto.validadoPorDocente) "✔ Validado por Docente UTP" else "⏳ Pendiente de validación",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (remoto.validadoPorDocente) Color(0xFF2E7D32) else Color(0xFFE65100)
                                            )
                                        }
                                        Row {
                                            IconButton(onClick = {
                                                remotoAEditar = remoto
                                                textoObsRemota = remoto.observacionDocente
                                                validadoRemoto = remoto.validadoPorDocente
                                            }) {
                                                Icon(Icons.Default.Edit, contentDescription = "Validar/Editar PUT", tint = MaterialTheme.colorScheme.primary)
                                            }
                                            IconButton(onClick = {
                                                usuarioViewModel.eliminarAvanceEnServidor(remoto.id)
                                            }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Eliminar DELETE", tint = MaterialTheme.colorScheme.error)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { mainViewModel.navigateTo(Screen.Home) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Volver al Inicio", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                OutlinedButton(
                    onClick = {
                        usuarioViewModel.cerrarSesion()
                        mainViewModel.navigateTo(Screen.Registro)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    itemAEditar?.let { item ->
        AlertDialog(
            onDismissRequest = { itemAEditar = null },
            title = { Text("Editar Observación Local (SQLite)") },
            text = {
                OutlinedTextField(
                    value = textoObservacion,
                    onValueChange = { textoObservacion = it },
                    label = { Text("Observación") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    usuarioViewModel.actualizarObservacionProgreso(item, textoObservacion)
                    itemAEditar = null
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { itemAEditar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    remotoAEditar?.let { remoto ->
        AlertDialog(
            onDismissRequest = { remotoAEditar = null },
            title = { Text("Validar Informe en Servidor (PUT)") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = textoObsRemota,
                        onValueChange = { textoObsRemota = it },
                        label = { Text("Observación UTP") }
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = validadoRemoto, onCheckedChange = { validadoRemoto = it })
                        Text("Validado por Docente UTP", fontSize = 13.sp)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    usuarioViewModel.actualizarAvanceEnServidor(remoto, textoObsRemota, validadoRemoto)
                    remotoAEditar = null
                }) {
                    Text("Actualizar en Servidor")
                }
            },
            dismissButton = {
                TextButton(onClick = { remotoAEditar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (mostrarDialogoAgregar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoAgregar = false },
            title = { Text("Registrar Avance de OA", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Selección rápida de asignatura:", fontSize = 12.sp, color = Color.Gray)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        opcionesPredefinidas.forEach { (asig, oa) ->
                            FilterChip(
                                selected = asignaturaInput == asig && oaInput == oa,
                                onClick = {
                                    asignaturaInput = asig
                                    oaInput = oa
                                },
                                label = { Text("$asig ($oa)", fontSize = 12.sp) }
                            )
                        }
                    }

                    HorizontalDivider()
                    Text("O escribe / edita manualmente:", fontSize = 12.sp, color = Color.Gray)

                    OutlinedTextField(
                        value = asignaturaInput,
                        onValueChange = { asignaturaInput = it },
                        label = { Text("Asignatura") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = oaInput,
                        onValueChange = { oaInput = it },
                        label = { Text("Código OA (ej. OA 4)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = observacionInput,
                        onValueChange = { observacionInput = it },
                        label = { Text("Observación docente") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = estadoLogradoInput,
                            onCheckedChange = { estadoLogradoInput = it }
                        )
                        Text("Objetivo logrado ✅", fontSize = 13.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = validadoDocenteInput,
                            onCheckedChange = { validadoDocenteInput = it }
                        )
                        Text("Validado profesionalmente UTP", fontSize = 13.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (asignaturaInput.isNotBlank() && oaInput.isNotBlank()) {
                            val obsFinal = observacionInput.trim().ifBlank { "Completado en plataforma" }
                            usuarioViewModel.registrarProgreso(
                                asignatura = asignaturaInput.trim(),
                                codigoOa = oaInput.trim(),
                                acertado = estadoLogradoInput,
                                usoModoRepaso = !estadoLogradoInput,
                                observacionDocente = obsFinal
                            )
                            usuarioViewModel.crearAvanceEnServidor(
                                asignatura = asignaturaInput.trim(),
                                codigoOa = oaInput.trim(),
                                observacion = obsFinal,
                                acertado = estadoLogradoInput,
                                validado = validadoDocenteInput
                            )
                            mostrarDialogoAgregar = false
                        }
                    }
                ) {
                    Text("Guardar y Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoAgregar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}