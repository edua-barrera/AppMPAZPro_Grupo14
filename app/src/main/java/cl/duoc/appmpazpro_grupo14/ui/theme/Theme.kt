package cl.duoc.appmpazpro_grupo14.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MpazLightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = OnPrimaryBlue,
    primaryContainer = PrimaryContainer,
    secondary = SecondaryOrange,
    onSecondary = OnSecondaryOrange,
    tertiary = TertiaryGreen,
    error = ErrorRed,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = OnSurfaceText,
    onSurface = OnSurfaceText
)

@Composable
fun AppMPAZPro_Grupo14Theme(
    content: @Composable () -> Unit
) {
    val colorScheme = MpazLightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}