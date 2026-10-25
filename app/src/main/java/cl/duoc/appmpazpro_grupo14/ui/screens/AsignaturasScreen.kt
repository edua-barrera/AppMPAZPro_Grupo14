package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel

data class ObjetivoAprendizaje(
    val codigo: String,
    val descripcion: String
)

data class AsignaturaItem(
    val nombre: String,
    val unidad: String,
    val ejeCurricular: String,
    val descripcion: String,
    val icono: String,
    val colorAcento: Color,
    val objetivos: List<ObjetivoAprendizaje>,
    val actividadSugerida: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturasScreen(viewModel: MainViewModel) {
    val asignaturas = remember {
        listOf(
            AsignaturaItem(
                nombre = "Lenguaje y Comunicación",
                unidad = "Unidad 1: Mundos de Fábula y Narración",
                ejeCurricular = "Lectura y Escritura Guiada",
                descripcion = "Lectura comprensiva de textos breves, fábulas con moraleja y caligrafía.",
                icono = "📖",
                colorAcento = Color(0xFFFF7043),
                objetivos = listOf(
                    ObjetivoAprendizaje("OA 4", "Profundizar la comprensión de narraciones leídas, extrayendo información explícita e implícita."),
                    ObjetivoAprendizaje("OA 12", "Escribir creativamente narraciones breves que incluyan inicio, desarrollo y desenlace.")
                ),
                actividadSugerida = "Desafío progresivo: Lee la fábula 'La Liebre y la Tortuga' y selecciona la enseñanza principal."
            ),
            AsignaturaItem(
                nombre = "Matemática",
                unidad = "Unidad 1: Números y Operaciones en la Escuela",
                ejeCurricular = "Números y Operaciones",
                descripcion = "Conteo hasta el 1.000, cálculo mental, tablas y multiplicación básica.",
                icono = "📐",
                colorAcento = Color(0xFF42A5F5),
                objetivos = listOf(
                    ObjetivoAprendizaje("OA 1", "Contar números del 0 al 1.000 de 5 en 5, de 10 en 10 y de 100 en 100."),
                    ObjetivoAprendizaje("OA 8", "Demostrar que comprenden las tablas de multiplicar hasta el 10 de manera progresiva.")
                ),
                actividadSugerida = "Ejercicios interactivos de conteo y resolución de problemas cotidianos de suma y multiplicación."
            ),
            AsignaturaItem(
                nombre = "Ciencias Naturales",
                unidad = "Unidad 1: Las Plantas y el Entorno Escolar",
                ejeCurricular = "Ciencias de la Vida",
                descripcion = "Estructura de las plantas, ciclo de vida, fotosíntesis y cuidado del medio ambiente.",
                icono = "🌱",
                colorAcento = Color(0xFF66BB6A),
                objetivos = listOf(
                    ObjetivoAprendizaje("OA 1", "Observar y describir las partes de una planta: raíz, tallo, hojas, flores y frutos."),
                    ObjetivoAprendizaje("OA 3", "Experimentar y explicar las necesidades básicas de una planta (luz, agua y aire).")
                ),
                actividadSugerida = "Actividad visual: Identifica las partes de la planta y asocia cada una con su función vital."
            ),
            AsignaturaItem(
                nombre = "Historia y Geografía",
                unidad = "Unidad 1: Pueblos Originarios y Paisajes de Chile",
                ejeCurricular = "Historia y Sociedad",
                descripcion = "Costumbres, modos de vida de pueblos originarios y reconocimiento de zonas chilenas.",
                icono = "🌎",
                colorAcento = Color(0xFFFFA726),
                objetivos = listOf(
                    ObjetivoAprendizaje("OA 1", "Describir los modos de vida de algunos pueblos originarios del territorio chileno actual."),
                    ObjetivoAprendizaje("OA 3", "Distinguir en mapas las principales zonas geográficas de Chile (Norte, Centro y Sur).")
                ),
                actividadSugerida = "Desafío didáctico: Ubica en el mapa las comunidades sedentarias y nómades según su entorno."
            )
        )
    }

    var asignaturaSeleccionada by remember { mutableStateOf<AsignaturaItem?>(null) }

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
                        text = "Troncales del Nivel (MVP)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "Plan curricular autorizado para 3.º Básico - Escuela Marcela Paz. Toca una asignatura para ver su unidad y objetivos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                items(asignaturas) { item ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { asignaturaSeleccionada = item },
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
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
                                Text(
                                    text = item.unidad,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
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

            asignaturaSeleccionada?.let { asig ->
                Dialog(
                    onDismissRequest = { asignaturaSeleccionada = null },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .fillMaxHeight(0.78f),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(asig.colorAcento)
                                    .padding(horizontal = 20.dp, vertical = 18.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(asig.icono, fontSize = 32.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = asig.nombre,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Escuela Marcela Paz",
                                            fontSize = 12.sp,
                                            color = Color.White.copy(alpha = 0.9f)
                                        )
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState())
                                    .padding(22.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Surface(
                                    color = asig.colorAcento.copy(alpha = 0.12f),
                                    shape = MaterialTheme.shapes.small,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = asig.unidad,
                                            fontWeight = FontWeight.Bold,
                                            color = asig.colorAcento,
                                            fontSize = 14.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Eje Curricular: ${asig.ejeCurricular}",
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            color = Color(0xFF475569)
                                        )
                                    }
                                }

                                Text(
                                    text = "Objetivos de Aprendizaje (Currículum Oficial):",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B)
                                )

                                asig.objetivos.forEach { oa ->
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text(
                                                text = oa.codigo,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = asig.colorAcento,
                                                modifier = Modifier.padding(end = 10.dp)
                                            )
                                            Text(
                                                text = oa.descripcion,
                                                fontSize = 13.sp,
                                                color = Color(0xFF334155),
                                                lineHeight = 18.sp
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider(color = Color(0xFFE2E8F0))

                                Text(
                                    text = "Actividad Didáctica Sugerida:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B)
                                )

                                Text(
                                    text = asig.actividadSugerida,
                                    fontSize = 13.sp,
                                    color = Color(0xFF475569),
                                    lineHeight = 19.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 14.dp)
                            ) {
                                Button(
                                    onClick = { asignaturaSeleccionada = null },
                                    colors = ButtonDefaults.buttonColors(containerColor = asig.colorAcento),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text("Entendido", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}