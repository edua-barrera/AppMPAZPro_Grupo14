package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import cl.duoc.appmpazpro_grupo14.viewmodel.EstadoViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel

data class ObjetivoAprendizaje(
    val codigo: String,
    val descripcion: String
)

data class PreguntaDesafio(
    val enunciado: String,
    val pistaRepaso: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int,
    val explicacion: String
)

data class AsignaturaItem(
    val nombre: String,
    val unidad: String,
    val ejeCurricular: String,
    val descripcion: String,
    val icono: String,
    val colorAcento: Color,
    val objetivos: List<ObjetivoAprendizaje>,
    val desafio: PreguntaDesafio
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturasScreen(
    viewModel: MainViewModel,
    estadoViewModel: EstadoViewModel
) {
    val modoRepasoActivo by estadoViewModel.activo.collectAsState()

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
                    ObjetivoAprendizaje("OA 12", "Escribir creativamente narraciones breves con inicio, desarrollo y desenlace.")
                ),
                desafio = PreguntaDesafio(
                    enunciado = "¿Cuál es el propósito principal de una fábula?",
                    pistaRepaso = "💡 Pista de Repaso: Las fábulas suelen tener animales que hablan y siempre nos dejan una enseñanza llamada moraleja.",
                    opciones = listOf(
                        "Informar una noticia de actualidad",
                        "Dejar una enseñanza o moraleja",
                        "Explicar una receta de cocina"
                    ),
                    respuestaCorrecta = 1,
                    explicacion = "¡Correcto! Las fábulas transmiten valores y aprendizajes a través de una moraleja final."
                )
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
                desafio = PreguntaDesafio(
                    enunciado = "Si en la sala de 3.º Básico hay 4 filas con 5 mesas cada una, ¿cuántas mesas hay en total?",
                    pistaRepaso = "💡 Pista de Repaso: La multiplicación es sumar un número varias veces: 4 veces 5 (4 x 5).",
                    opciones = listOf(
                        "9 mesas",
                        "20 mesas",
                        "15 mesas"
                    ),
                    respuestaCorrecta = 1,
                    explicacion = "¡Exacto! 4 filas por 5 mesas equivale a 4 x 5 = 20 mesas en total."
                )
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
                desafio = PreguntaDesafio(
                    enunciado = "¿Qué parte de la planta absorbe el agua y los nutrientes desde la tierra?",
                    pistaRepaso = "💡 Pista de Repaso: Se encuentra oculta bajo la tierra y sostiene a la planta firme en el suelo.",
                    opciones = listOf(
                        "Las hojas",
                        "La raíz",
                        "El fruto"
                    ),
                    respuestaCorrecta = 1,
                    explicacion = "¡Excelente! La raíz es el órgano encargado de absorber el agua y minerales del suelo."
                )
            ),
            AsignaturaItem(
                nombre = "Historia y Geografía",
                unidad = "Unidad 1: Pueblos Originarios y Paisajes de Chile",
                ejeCurricular = "Historia y Sociedad",
                descripcion = "Costumbres, modos de vida de pueblos originarios y reconocimiento de zonas chilenas.",
                icono = "🌎",
                colorAcento = Color(0xFFFFA726),
                objetivos = listOf(
                    ObjetivoAprendizaje("OA 1", "Describir los modos de vida de pueblos originarios del territorio chileno actual."),
                    ObjetivoAprendizaje("OA 3", "Distinguir en mapas las principales zonas geográficas de Chile (Norte, Centro y Sur).")
                ),
                desafio = PreguntaDesafio(
                    enunciado = "¿Qué pueblo originario habitaba la zona norte de Chile y vivía de manera sedentaria?",
                    pistaRepaso = "💡 Pista de Repaso: Vivían en aldeas fijas y construían terrazas de cultivo en el desierto y valles.",
                    opciones = listOf(
                        "Atacameños (Lickanantay)",
                        "Kawésqar",
                        "Chonos"
                    ),
                    respuestaCorrecta = 0,
                    explicacion = "¡Muy bien! Los atacameños eran sedentarios, habitaban el norte grande y practicaban la agricultura en terrazas."
                )
            )
        )
    }

    var asignaturaSeleccionada by remember { mutableStateOf<AsignaturaItem?>(null) }
    var opcionSeleccionada by remember { mutableStateOf<Int?>(null) }
    var retroalimentacionVisible by remember { mutableStateOf(false) }

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
                        text = "Plan curricular autorizado para 3.º Básico - Escuela Marcela Paz. Toca una asignatura para ver su contenido y resolver el desafío didáctico.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                items(asignaturas) { item ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                asignaturaSeleccionada = item
                                opcionSeleccionada = null
                                retroalimentacionVisible = false
                            },
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
                            .fillMaxHeight(0.85f),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
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
                                            text = "Escuela Escritora Marcela Paz",
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
                                    .padding(20.dp),
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
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Eje Curricular: ${asig.ejeCurricular}",
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            color = Color(0xFF475569)
                                        )
                                    }
                                }

                                Text(
                                    text = "Objetivos de Aprendizaje Oficiales:",
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
                                                modifier = Modifier.padding(end = 8.dp)
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
                                    text = "🎯 Desafío Formativo de la Unidad:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF1E293B)
                                )

                                AnimatedVisibility(visible = modoRepasoActivo == true) {
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                                        border = BorderStroke(1.dp, Color(0xFFF59E0B)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = asig.desafio.pistaRepaso,
                                            fontSize = 12.sp,
                                            color = Color(0xFF92400E),
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(12.dp),
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Text(
                                    text = asig.desafio.enunciado,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B)
                                )

                                // Alternativas
                                asig.desafio.opciones.forEachIndexed { indice, opcion ->
                                    val esSeleccionada = opcionSeleccionada == indice
                                    val esCorrecta = indice == asig.desafio.respuestaCorrecta

                                    val colorFondo = when {
                                        retroalimentacionVisible && esCorrecta -> Color(0xFFDCFCE7)
                                        retroalimentacionVisible && esSeleccionada && !esCorrecta -> Color(0xFFFEE2E2)
                                        esSeleccionada -> asig.colorAcento.copy(alpha = 0.15f)
                                        else -> Color(0xFFF8FAFC)
                                    }

                                    val colorBorde = when {
                                        retroalimentacionVisible && esCorrecta -> Color(0xFF16A34A)
                                        retroalimentacionVisible && esSeleccionada && !esCorrecta -> Color(0xFFDC2626)
                                        esSeleccionada -> asig.colorAcento
                                        else -> Color(0xFFE2E8F0)
                                    }

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                opcionSeleccionada = indice
                                                retroalimentacionVisible = true
                                            },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(containerColor = colorFondo),
                                        border = BorderStroke(1.dp, colorBorde)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${('A' + indice)})",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = asig.colorAcento,
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                            Text(
                                                text = opcion,
                                                fontSize = 13.sp,
                                                fontWeight = if (esSeleccionada) FontWeight.Bold else FontWeight.Normal,
                                                color = Color(0xFF1E293B)
                                            )
                                        }
                                    }
                                }

                                AnimatedVisibility(visible = retroalimentacionVisible) {
                                    val acierto = opcionSeleccionada == asig.desafio.respuestaCorrecta
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (acierto) Color(0xFFF0FDF4) else Color(0xFFFEF2F2)
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = if (acierto) {
                                                "✅ ${asig.desafio.explicacion}"
                                            } else {
                                                "❌ Respuesta incorrecta. Vuelve a revisar la pregunta o activa el Modo Repaso para ver la pista didáctica."
                                            },
                                            color = if (acierto) Color(0xFF15803D) else Color(0xFFB91C1C),
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(12.dp),
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 12.dp)
                            ) {
                                Button(
                                    onClick = { asignaturaSeleccionada = null },
                                    colors = ButtonDefaults.buttonColors(containerColor = asig.colorAcento),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text("Cerrar Desafío", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}