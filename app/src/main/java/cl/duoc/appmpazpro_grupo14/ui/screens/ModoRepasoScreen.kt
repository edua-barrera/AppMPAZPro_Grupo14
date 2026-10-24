package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.EstadoViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModoRepasoScreen(
    estadoViewModel: EstadoViewModel,
    mainViewModel: MainViewModel
) {
    val estado by estadoViewModel.activo.collectAsState()
    val mostrarMensaje by estadoViewModel.mostrarMensaje.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Modo Repaso Guiado", fontWeight = FontWeight.Bold, color = Color.White) },
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
            if (estado == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Cargando preferencias del estudiante...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B)
                    )
                }
            } else {
                val estaActivo = estado!!
                val colorBotonAnimado by animateColorAsState(
                    targetValue = if (estaActivo) Color(0xFF2E7D32) else Color(0xFF64748B),
                    animationSpec = tween(durationMillis = 400),
                    label = "colorBotonAnimado"
                )

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
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(
                                            if (estaActivo) Color(0xFFE8F5E9) else Color(0xFFF1F5F9),
                                            MaterialTheme.shapes.medium
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(if (estaActivo) "💡" else "💤", fontSize = 24.sp)
                                }
                                Column {
                                    Text(
                                        text = "Estado de Estudio Didáctico",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF1E293B)
                                    )
                                    Text(
                                        text = if (estaActivo) "Modo Intensivo Activo" else "Modo Regular Estándar",
                                        fontSize = 13.sp,
                                        color = if (estaActivo) Color(0xFF2E7D32) else Color(0xFF64748B),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Text(
                                text = "El Modo Repaso optimiza las actividades para reforzar contenidos clave de 3.º Básico antes de las evaluaciones formativas.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Button(
                        onClick = { estadoViewModel.alternarEstado() },
                        colors = ButtonDefaults.buttonColors(containerColor = colorBotonAnimado),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = if (estaActivo) "Desactivar Modo Repaso" else "Activar Modo Repaso",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    AnimatedVisibility(
                        visible = mostrarMensaje,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "✓ Preferencia guardada en DataStore",
                                color = Color(0xFF166534),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(14.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 14.sp
                            )
                        }
                    }

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Aspectos pedagógicos que activa:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF334155))
                            Text("• Guías interactivas con retroalimentación inmediata", fontSize = 12.sp, color = Color(0xFF64748B))
                            Text("• Temporizador adaptativo de 15 minutos para niños", fontSize = 12.sp, color = Color(0xFF64748B))
                            Text("• Resúmenes visuales con tipografía reforzada", fontSize = 12.sp, color = Color(0xFF64748B))
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

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
}