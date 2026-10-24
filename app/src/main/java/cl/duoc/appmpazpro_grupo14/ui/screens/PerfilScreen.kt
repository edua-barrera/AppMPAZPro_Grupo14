package cl.duoc.appmpazpro_grupo14.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.ui.components.ImagenInteligente
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel
import cl.duoc.appmpazpro_grupo14.viewmodel.PerfilViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Foto de Perfil", fontWeight = FontWeight.Bold, color = Color.White) },
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImagenInteligente(uri = imagenUri)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (imagenUri != null) "Fotografía cargada correctamente" else "Sin fotografía asignada",
                            fontSize = 13.sp,
                            color = if (imagenUri != null) Color(0xFF2E7D32) else Color(0xFF64748B),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("📷  Tomar Foto con Cámara", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("🖼️  Seleccionar de Galería", fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(28.dp))

                TextButton(onClick = { mainViewModel.navigateTo(Screen.Home) }) {
                    Text("Volver al Inicio", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}