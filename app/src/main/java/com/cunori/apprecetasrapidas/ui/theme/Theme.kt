package com.cunori.apprecetasrapidas.ui.theme

import android.os.Build
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = BasilGreen80,
    secondary = Tomato80,
    tertiary = Corn80,
    background = DarkSurface,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF374039),
    surfaceContainerHighest = Color(0xFF222A25),
    primaryContainer = Color(0xFF17472F),
    secondaryContainer = Color(0xFF5E2015),
    tertiaryContainer = Color(0xFF533B00)
)

private val LightColorScheme = lightColorScheme(
    primary = BasilGreen40,
    secondary = Tomato40,
    tertiary = Corn40,
    background = WarmSurface,
    surface = WarmSurface,
    surfaceVariant = WarmSurfaceVariant,
    surfaceContainer = SoftCream,
    surfaceContainerHighest = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD9F0DF),
    secondaryContainer = Color(0xFFFFDED6),
    tertiaryContainer = Color(0xFFFFE9B3),
    onBackground = Ink,
    onSurface = Ink,
    onSurfaceVariant = MutedInk
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp)
)

@Composable
fun AppRecetasRapidasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
