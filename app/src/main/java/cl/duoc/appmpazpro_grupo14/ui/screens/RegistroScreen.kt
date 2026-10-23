package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.UsuarioViewModel

@Composable
fun RegistroScreen(
    usuarioViewModel: UsuarioViewModel,
    mainViewModel: MainViewModel
) {
    val estado by usuarioViewModel.estado.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro Estudiante - 3.º Básico", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = usuarioViewModel::onNombreChange,
            label = { Text("Nombre del Estudiante") },
            isError = estado.errores.nombre != null,
            supportingText = { estado.errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.correo,
            onValueChange = usuarioViewModel::onCorreoChange,
            label = { Text("Correo Institucional (Ficticio)") },
            isError = estado.errores.correo != null,
            supportingText = { estado.errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.clave,
            onValueChange = usuarioViewModel::onClaveChange,
            label = { Text("Clave de Acceso Didáctico") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.clave != null,
            supportingText = { estado.errores.clave?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.direccion,
            onValueChange = usuarioViewModel::onDireccionChange,
            label = { Text("Comuna / Dirección") },
            isError = estado.errores.direccion != null,
            supportingText = { estado.errores.direccion?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = estado.aceptaTerminos, onCheckedChange = usuarioViewModel::onAceptarTerminosChange)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Acepto las normas de la plataforma")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (usuarioViewModel.validarFormulario()) {
                    mainViewModel.navigateTo(Screen.Resumen)
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Registrar Estudiante")
        }

        OutlinedButton(onClick = { mainViewModel.navigateTo(Screen.Home) }, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al Inicio")
        }
    }
}