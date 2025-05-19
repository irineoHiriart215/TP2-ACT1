package com.example.tp2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Secondary,
    onSecondary = Color.White,
    background = Background,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = TextPrimary,
    error = ErrorRed,
    onError = Color.White
)


@Composable
fun TP2Theme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}