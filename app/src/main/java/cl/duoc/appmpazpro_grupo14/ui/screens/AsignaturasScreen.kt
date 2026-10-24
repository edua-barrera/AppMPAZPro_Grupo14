package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel

data class AsignaturaItem(
    val nombre: String,
    val descripcion: String,
    val icono: String,
    val colorAcento: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturasScreen(viewModel: MainViewModel) {
    val asignaturas = remember {
        listOf(
            AsignaturaItem("Lenguaje y Comunicación", "Lectura comprensiva, fábulas y caligrafía.", "📖", Color(0xFFFF7043)),
            AsignaturaItem("Matemática", "Multiplicaciones, tablas y figuras geométricas.", "📐", Color(0xFF42A5F5)),
            AsignaturaItem("Ciencias Naturales", "Plantas, ecosistemas y cuidado ambiental.", "🌱", Color(0xFF66BB6A)),
            AsignaturaItem("Historia y Geografía", "Pueblos originarios y zonas de Chile.", "🌎", Color(0xFFFFA726))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asignaturas 3.º Básico", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { viewModel.navigateTo(Screen.Home) },
                    label = { Text("Inicio") },
                    icon = { Text("🏠", fontSize = 20.sp) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    label = { Text("Asignaturas") },
                    icon = { Text("📚", fontSize = 20.sp) }
                )
            }
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = "Troncales del Nivel",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "Selecciona una materia para acceder al material didáctico.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                items(asignaturas) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .background(item.colorAcento.copy(alpha = 0.18f), MaterialTheme.shapes.medium),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = item.icono, fontSize = 28.sp)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.descripcion,
                                    fontSize = 13.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}