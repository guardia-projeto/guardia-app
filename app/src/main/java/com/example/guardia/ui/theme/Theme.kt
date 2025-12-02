package com.example.guardia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Cores definidas no seu Color.kt
private val DarkColorScheme = darkColorScheme(
    primary = brand700, 
    onPrimary = neutral100, 
    secondary = brand950,
    onSecondary = neutral100, 
    tertiary = yellow50,
    background = brand1000,
    onBackground = neutral100,
    surface = brand900, 
    onSurface = neutral100
)

private val LightColorScheme = lightColorScheme(
    primary = brand900, //cor do texto e botões
    onPrimary = neutral100, //cards e textbox
    secondary = brand950, // card home e chat popup
    onSecondary = neutral100, // texto card home
    tertiary = yellow50, // texto login/cadastro inferior
    onTertiary = brand650, // cor dos icones perfil
    background = brand1200, //cor do fundo
    onBackground = brand900, //cor do texto de fundo
    surface = neutral100,
    onSurface = brand900,
)

@Composable
fun GuardiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // AQUI: Desativamos a Cor Dinâmica para usar as cores da nossa marca
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
        content = content
    )
}
