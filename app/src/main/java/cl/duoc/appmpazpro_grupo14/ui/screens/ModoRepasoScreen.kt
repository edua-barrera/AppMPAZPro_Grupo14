package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.EstadoViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel

@Composable
fun ModoRepasoScreen(
    estadoViewModel: EstadoViewModel,
    mainViewModel: MainViewModel
) {
    val estado by estadoViewModel.activo.collectAsState()
    val mostrarMensaje by estadoViewModel.mostrarMensaje.collectAsState()

    if (estado == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val estaActivo = estado!!
        val colorAnimado by animateColorAsState(
            targetValue = if (estaActivo) Color(0xFF4CAF50) else Color(0xFFB0BEC5),
            animationSpec = tween(500),
            label = "colorAnimado"
        )
        val textoBoton by remember(estaActivo) {
            derivedStateOf { if (estaActivo) "Desactivar Modo Repaso" else "Activar Modo Repaso" }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Configuración de Estudio", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { estadoViewModel.alternarEstado() },
                colors = ButtonDefaults.buttonColors(containerColor = colorAnimado),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text(text = textoBoton, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = mostrarMensaje) {
                Text("¡Estado guardado exitosamente!", color = Color(0xFF4CAF50), style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(48.dp))
            Button(onClick = { mainViewModel.navigateTo(Screen.Home) }) {
                Text("Volver al Inicio")
            }
        }
    }
}