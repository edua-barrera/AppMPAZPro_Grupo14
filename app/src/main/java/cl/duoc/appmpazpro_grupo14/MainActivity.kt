package cl.duoc.appmpazpro_grupo14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.appmpazpro_grupo14.navigation.NavigationEvent
import cl.duoc.appmpazpro_grupo14.navigation.Screen
import cl.duoc.appmpazpro_grupo14.ui.screens.*
import cl.duoc.appmpazpro_grupo14.ui.theme.AppMPAZPro_Grupo14Theme
import cl.duoc.appmpazpro_grupo14.viewmodel.*
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMPAZPro_Grupo14Theme {
                val mainViewModel: MainViewModel = viewModel()
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val estadoViewModel: EstadoViewModel = viewModel()
                val perfilViewModel: PerfilViewModel = viewModel()

                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    mainViewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    event.popupToRoute?.let { popup ->
                                        popUpTo(popup.route) { inclusive = event.inclusive }
                                    }
                                    launchSingleTop = event.singleTop
                                }
                            }
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(viewModel = mainViewModel)
                        }
                        composable(Screen.Asignaturas.route) {
                            AsignaturasScreen(viewModel = mainViewModel)
                        }
                        composable(Screen.Registro.route) {
                            RegistroScreen(usuarioViewModel = usuarioViewModel, mainViewModel = mainViewModel)
                        }
                        composable(Screen.Resumen.route) {
                            ResumenScreen(usuarioViewModel = usuarioViewModel, mainViewModel = mainViewModel)
                        }
                        composable(Screen.ModoRepaso.route) {
                            ModoRepasoScreen(estadoViewModel = estadoViewModel, mainViewModel = mainViewModel)
                        }
                        composable(Screen.Perfil.route) {
                            PerfilScreen(perfilViewModel = perfilViewModel, mainViewModel = mainViewModel)
                        }
                    }
                }
            }
        }
    }
}