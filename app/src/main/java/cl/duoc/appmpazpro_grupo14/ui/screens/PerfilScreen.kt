package cl.duoc.appmpazpro_grupo14.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.ui.components.ImagenInteligente
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.PerfilViewModel
import java.io.File

@Composable
fun PerfilScreen(
    perfilViewModel: PerfilViewModel,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val imagenUri by perfilViewModel.imagenUri.collectAsState()
    var uriTemporal by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { exito ->
        if (exito) uriTemporal?.let { perfilViewModel.actualizarImagen(it) }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { perfilViewModel.actualizarImagen(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Perfil Fotográfico", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(32.dp))

        ImagenInteligente(uri = imagenUri)

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                val archivoTemporal = File.createTempFile("foto_perfil_", ".jpg", context.cacheDir)
                uriTemporal = FileProvider.getUriForFile(
                    context,
                    "cl.duoc.appmpazpro_grupo14.fileprovider",
                    archivoTemporal
                )
                cameraLauncher.launch(uriTemporal!!)
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Tomar Foto con Cámara")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Seleccionar de la Galería")
        }

        Spacer(modifier = Modifier.height(48.dp))
        TextButton(onClick = { mainViewModel.navigateTo(Screen.Home) }) {
            Text("Volver al Inicio")
        }
    }
}