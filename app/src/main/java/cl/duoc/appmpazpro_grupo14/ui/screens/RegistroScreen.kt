package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    usuarioViewModel: UsuarioViewModel,
    mainViewModel: MainViewModel
) {
    val estado by usuarioViewModel.estado.collectAsState()
    val errorLogin by usuarioViewModel.errorLogin.collectAsState()

    var tabSeleccionada by remember { mutableIntStateOf(0) }
    var correoLogin by remember { mutableStateOf("") }
    var claveLogin by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Acceso Escolar 3.º Básico", fontWeight = FontWeight.Bold, color = Color.White) },
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
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    SegmentedButton(
                        selected = tabSeleccionada == 0,
                        onClick = { tabSeleccionada = 0 },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                    ) {
                        Text("Iniciar Sesión", fontWeight = FontWeight.SemiBold)
                    }
                    SegmentedButton(
                        selected = tabSeleccionada == 1,
                        onClick = { tabSeleccionada = 1 },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                    ) {
                        Text("Registrarse", fontWeight = FontWeight.SemiBold)
                    }
                }

                if (tabSeleccionada == 0) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "Ingresar a mi Credencial",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Usa el correo y clave registrados en la base de datos local.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B)
                            )

                            OutlinedTextField(
                                value = correoLogin,
                                onValueChange = { correoLogin = it },
                                label = { Text("Correo registrado") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = claveLogin,
                                onValueChange = { claveLogin = it },
                                label = { Text("Clave de acceso") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            errorLogin?.let { mensaje ->
                                Text(
                                    text = mensaje,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (usuarioViewModel.iniciarSesion(correoLogin, claveLogin)) {
                                mainViewModel.navigateTo(Screen.Resumen)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text("Ingresar a mi Perfil", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "Ficha Escolar 3.º Básico",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Ingresa los antecedentes del alumno para generar su credencial oficial.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B)
                            )

                            OutlinedTextField(
                                value = estado.nombre,
                                onValueChange = usuarioViewModel::onNombreChange,
                                label = { Text("Nombre Completo") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                                isError = estado.errores.nombre != null,
                                supportingText = { estado.errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = estado.correo,
                                onValueChange = usuarioViewModel::onCorreoChange,
                                label = { Text("Correo Institucional / Apoderado") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                                isError = estado.errores.correo != null,
                                supportingText = { estado.errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = estado.clave,
                                onValueChange = usuarioViewModel::onClaveChange,
                                label = { Text("Clave de Acceso Didáctico") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                                visualTransformation = PasswordVisualTransformation(),
                                isError = estado.errores.clave != null,
                                supportingText = { estado.errores.clave?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = estado.direccion,
                                onValueChange = usuarioViewModel::onDireccionChange,
                                label = { Text("Comuna / Dirección") },
                                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                                isError = estado.errores.direccion != null,
                                supportingText = { estado.errores.direccion?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Checkbox(
                                    checked = estado.aceptaTerminos,
                                    onCheckedChange = usuarioViewModel::onAceptarTerminosChange
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Acepto el reglamento didáctico escolar",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF334155)
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (usuarioViewModel.validarYGuardarUsuario()) {
                                mainViewModel.navigateTo(Screen.Resumen)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text("Registrar y Ver Credencial", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = { mainViewModel.navigateTo(Screen.Home) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Volver al Inicio")
                }
            }
        }
    }
}