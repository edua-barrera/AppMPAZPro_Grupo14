package cl.duoc.appmpazpro_grupo14.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Asignaturas : Screen("asignaturas")
    data object Registro : Screen("registro")
    data object Resumen : Screen("resumen")
    data object ModoRepaso : Screen("modo_repaso")
    data object Perfil : Screen("perfil")
}