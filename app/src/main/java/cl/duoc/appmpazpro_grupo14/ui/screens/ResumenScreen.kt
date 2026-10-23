package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    usuarioViewModel: UsuarioViewModel,
    mainViewModel: MainViewModel
) {
    val estado by usuarioViewModel.estado.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {
        Text("Credencial del Estudiante", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Nombre: ${estado.nombre}", style = MaterialTheme.typography.bodyLarge)
                Text("Correo: ${estado.correo}", style = MaterialTheme.typography.bodyLarge)
                Text("Dirección: ${estado.direccion}", style = MaterialTheme.typography.bodyLarge)
                Text("Contraseña: ${"*".repeat(estado.clave.length)}", style = MaterialTheme.typography.bodyLarge)
                Text("Normas Aceptadas: ${if (estado.aceptaTerminos) "Sí" else "No"}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { mainViewModel.navigateTo(Screen.Home) }, modifier = Modifier.fillMaxWidth()) {
            Text("Finalizar y Volver al Inicio")
        }
    }
}