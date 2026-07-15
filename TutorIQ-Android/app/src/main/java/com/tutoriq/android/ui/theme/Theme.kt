package com.tutoriq.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onBackground = OnBackground,
    onSurface = OnSurface,
    error = Error
)

@Composable
fun TutorIQTheme(content: @Composable () -> Unit) {
    val colorScheme = LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
