package cl.duoc.appmpazpro_grupo14.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
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
import androidx.core.content.ContextCompat
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

    // 1. Launcher para la captura de foto
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exitoso ->
        if (exitoso) {
            uriTemporal?.let { uri ->
                perfilViewModel.actualizarImagen(uri)
            }
        }
    }

    fun lanzarCamara() {
        try {
            val directorioImagenes = File(context.cacheDir, "mis_imagenes").apply { mkdirs() }
            val archivoTemporal = File.createTempFile("foto_perfil_", ".jpg", directorioImagenes)

            val uri = FileProvider.getUriForFile(
                context,
                "cl.duoc.appmpazpro_grupo14.fileprovider",
                archivoTemporal
            )
            uriTemporal = uri
            cameraLauncher.launch(uri)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al preparar la cámara: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { fueConcedido ->
        if (fueConcedido) {
            lanzarCamara()
        } else {
            Toast.makeText(context, "Se necesita permiso de cámara para la credencial escolar", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
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
                        val tienePermiso = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED

                        if (tienePermiso) {
                            lanzarCamara()
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
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