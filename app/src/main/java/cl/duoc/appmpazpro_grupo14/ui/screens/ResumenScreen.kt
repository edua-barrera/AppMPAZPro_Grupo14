package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
                title = { Text("Credencial Escolar", fontWeight = FontWeight.Bold, color = Color.White) },
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
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
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
                                    Text("ESCUELA MARCELA PAZ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text("Ficha Estudiantil - 3.º Básico", color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp)
                                }
                                Text("🎓", fontSize = 24.sp)
                            }
                        }

                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("Nombre Completo:", color = Color.Gray, fontSize = 12.sp)
                            Text(estado.nombre.ifBlank { "No registrado" }, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Text("Correo Electrónico:", color = Color.Gray, fontSize = 12.sp)
                            Text(estado.correo.ifBlank { "No registrado" }, fontWeight = FontWeight.Medium, fontSize = 15.sp)

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Text("Comuna / Dirección:", color = Color.Gray, fontSize = 12.sp)
                            Text(estado.direccion.ifBlank { "No registrada" }, fontWeight = FontWeight.Medium, fontSize = 15.sp)

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Text("Estado del Reglamento Didáctico:", color = Color.Gray, fontSize = 12.sp)
                            Text(
                                if (estado.aceptaTerminos) "✅ Aceptado formalmente" else "❌ Pendiente",
                                color = if (estado.aceptaTerminos) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Button(
                    onClick = { mainViewModel.navigateTo(Screen.Home) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Finalizar y Volver al Inicio", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}