package com.example.englishup.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Green,
    onPrimary = Bg,
    secondary = Blue,
    onSecondary = TextPrimary,
    tertiary = Purple,
    background = Bg2,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = Surface2,
    onSurfaceVariant = TextSecondary,
    outline = Border,
    outlineVariant = Border2,
    error = Red,
    onError = TextPrimary,
)

@Composable
fun EnglishUPTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}