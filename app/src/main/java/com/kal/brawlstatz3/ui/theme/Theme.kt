package com.kal.brawlstatz3.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

enum class AppTheme {
    DYNAMIC,
    LIGHT,
    DARK,
    CLASSIC,
    VARIANT,
    HIGH_CONTRAST
}

private val ClassicColorScheme = darkColorScheme(
    primary = Color(0xFF00BCD4),          // Cyan → strong accent
    onPrimary = Color.Black,              // Text on cyan

    inversePrimary = Color(0xFF1A237E),   // Muted indigo, works as inverse

    background = Color(0xFF00052A),       // Dark navy BG (less harsh than pure #000341)
    onBackground = Color(0xFFE0E0E0),     // Light gray text

    surface = Color(0xFF00052A),          // Default surface (nav bar)
    onSurface = Color(0xFFE0E0E0),        // Light text/icons on surface

    surfaceContainer = Color(0xFF0A1550), // BottomNav background
    surfaceContainerHighest = Color(0xFF102080), // Cards
    surfaceContainerHigh = Color(0xFF102080),    // Searchbar
    surfaceContainerLow = Color(0xFF00052A),     // Low-level surfaces

    surfaceVariant = Color(0xFF1E3A8A),   // Slightly lighter variant (blue-500ish)
    onSurfaceVariant = Color(0xFFB0BEC5), // Text/icons on variant

    secondaryContainer = Color(0xFF00ACC1),       // Softer cyan
    onSecondaryContainer = Color(0xFF00111F),     // Dark text for readability
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00BCD4),
    onPrimary = Color.Black,
    inversePrimary = Color(0xFF1A237E)
)

private val ClassicVariantColorScheme = darkColorScheme(
    primary = Color(0xFF00BCD4),          // Cyan accent
    onPrimary = Color.Black,              // Text/icons on cyan

    inversePrimary = Color(0xFF1A237E),   // Indigo for inverse UI elements

    background = Color(0xFF0B1221),       // Dark navy (soft, not pure black)
    onBackground = Color(0xFFE0E0E0),     // Light gray text

    surface = Color(0xFF0B1221),          // Slightly lighter than background (nav bar)
    onSurface = Color(0xFFE0E0E0),        // Light text/icons

    surfaceContainer = Color(0xFF18213D), // BottomNav background
    surfaceContainerHighest = Color(0xFF222C4D), // Cards
    surfaceContainerHigh = Color(0xFF222C4D),    // Searchbar
    surfaceContainerLow = Color(0xFF0B1221),     // Base layer

    surfaceVariant = Color(0xFF2F3F70),   // Muted blue variant
    onSurfaceVariant = Color(0xFFB0BEC5), // Muted gray text/icons

    secondaryContainer = Color(0xFF00ACC1), // Softer cyan accent
    onSecondaryContainer = Color(0xFF00111F)

)
private val DarkColorSchemeHighContrast = darkColorScheme(
    primary = Color(0xFF00BCD4),          // Cyan accent
    onPrimary = Color.Black,              // Text/icons on cyan

    inversePrimary = Color(0xFF1A237E),   // Deep indigo for contrast

    background = Color(0xFF000000),       // Pure AMOLED black
    onBackground = Color(0xFFE0E0E0),     // Light gray text

    surface = Color(0xFF000000),          // Same as background (nav bar)
    onSurface = Color(0xFFE0E0E0),        // Light text/icons

    surfaceContainer = Color(0xFF0A0A0A), // Subtle dark gray (BottomNav)
    surfaceContainerHighest = Color(0xFF171717), // Cards
    surfaceContainerHigh = Color(0xFF101010),    // Searchbar
    surfaceContainerLow = Color(0xFF000000),     // Base layer (matches background)

    surfaceVariant = Color(0xFF1E3A8A),   // Indigo/blue accent variant
    onSurfaceVariant = Color(0xFFB0BEC5), // Muted gray text/icons

    secondaryContainer = Color(0xFF00ACC1), // Softer cyan
    onSecondaryContainer = Color(0xFF00111F)

)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6A1B9A),          // Rich purple accent
    onPrimary = Color.White,              // White text/icons on purple

    inversePrimary = Color(0xFFBA68C8),   // Softer lavender for inverse elements

    background = Color(0xFFF8F5FC),       // Very light lavender-gray
    onBackground = Color(0xFF1A1A1A),     // Dark text

    surface = Color(0xFFFFFFFF),          // Default surface (nav bar, dialogs)
    onSurface = Color(0xFF1A1A1A),        // Dark text/icons

    surfaceContainer = Color(0xFFF0E8F9), // BottomNav background
    surfaceContainerHighest = Color(0xFFE6DFF3), // Cards
    surfaceContainerHigh = Color(0xFFE6DFF3),    // Searchbar
    surfaceContainerLow = Color(0xFFF8F5FC),     // Base layer

    surfaceVariant = Color(0xFFD1C4E9),   // Muted lavender variant
    onSurfaceVariant = Color(0xFF4A4458), // Muted dark text/icons

    secondaryContainer = Color(0xFFE1BEE7), // Soft pastel purple
    onSecondaryContainer = Color(0xFF2E003E)
)
@Composable
fun BrawlStatZTheme(
    theme: AppTheme = AppTheme.CLASSIC,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val context = LocalContext.current

    val colorScheme = when (theme) {
        AppTheme.DYNAMIC -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (isSystemInDarkTheme()) dynamicDarkColorScheme(context)
                else dynamicLightColorScheme(context)
            } else {
                if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
            }
        }
        AppTheme.DARK -> DarkColorScheme
        AppTheme.LIGHT -> LightColorScheme
        AppTheme.CLASSIC -> ClassicColorScheme
        AppTheme.VARIANT -> ClassicVariantColorScheme
        AppTheme.HIGH_CONTRAST -> DarkColorSchemeHighContrast
    }

    val statusBarThemeIsLight = theme == AppTheme.LIGHT

    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = statusBarThemeIsLight

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
