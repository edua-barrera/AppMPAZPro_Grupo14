package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                                if (estado.aceptaTerminos) "✅ Aceptado formalmente" else "❌ Pendiente de aceptación",
                                color = if (estado.aceptaTerminos) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
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
                                text = "Informe de Seguimiento Formativo",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF1E293B)
                            )
                            Surface(
                                color = Color(0xFFE0F2FE),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "Uso Docente",
                                    color = Color(0xFF0369A1),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "Registro formativo vinculado a los Objetivos de Aprendizaje de Unidad 1. No emite juicios diagnósticos definitivos.",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            lineHeight = 16.sp
                        )

                        HorizontalDivider(color = Color(0xFFE2E8F0))

                        Text("• Fortaleza observada: Comprensión de textos breves y fábulas (OA 4).", fontSize = 12.sp, color = Color(0xFF334155))
                        Text("• En desarrollo: Ejercitación de conteo y tablas de multiplicación (OA 8).", fontSize = 12.sp, color = Color(0xFF334155))
                        Text("• Recomendación UTP: Activar Modo Repaso guiado para acompañar desafíos progresivos.", fontSize = 12.sp, color = Color(0xFF15803D), fontWeight = FontWeight.Medium)
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
            }
        }
    }
}