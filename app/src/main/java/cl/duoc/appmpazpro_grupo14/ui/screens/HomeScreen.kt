package cl.duoc.appmpazpro_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Menú MPAZ PRO", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Asignaturas Troncales") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; viewModel.navigateTo(Screen.Asignaturas) }
                )
                NavigationDrawerItem(
                    label = { Text("Registro Estudiante") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; viewModel.navigateTo(Screen.Registro) }
                )
                NavigationDrawerItem(
                    label = { Text("Modo Repaso (DataStore)") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; viewModel.navigateTo(Screen.ModoRepaso) }
                )
                NavigationDrawerItem(
                    label = { Text("Perfil Fotográfico (Cámara)") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; viewModel.navigateTo(Screen.Perfil) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Inicio - Escuela Marcela Paz") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menú")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Bienvenido a MPAZ PRO 3.º", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Desliza desde la izquierda o usa el ícono superior para navegar por los módulos de la rúbrica.")
            }
        }
    }
}