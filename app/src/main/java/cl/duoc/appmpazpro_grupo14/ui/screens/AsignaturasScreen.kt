package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel

@Composable
fun AsignaturasScreen(viewModel: MainViewModel) {
    val items = listOf(Screen.Home, Screen.Asignaturas)
    var selectedItem by remember { mutableIntStateOf(1) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            viewModel.navigateTo(screen)
                        },
                        label = { Text(if (index == 0) "Inicio" else "Asignaturas") },
                        icon = { Text(if (index == 0) "🏠" else "📚") }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Asignaturas 3.º Básico", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("• Lenguaje y Comunicación", style = MaterialTheme.typography.bodyLarge)
                    Text("• Matemática", style = MaterialTheme.typography.bodyLarge)
                    Text("• Ciencias Naturales", style = MaterialTheme.typography.bodyLarge)
                    Text("• Historia, Geografía y CCSS", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}