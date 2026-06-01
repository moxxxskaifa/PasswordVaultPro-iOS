package com.passwordvault.app.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val Light = lightColorScheme(primary=Primary, onPrimary=Color.White, background=Background, onBackground=OnBackground, surface=Surface, onSurface=OnBackground, surfaceVariant=Color(0xFFF0EDE8), onSurfaceVariant=OnSurfaceVariant)
@Composable fun AppTheme(content: @Composable () -> Unit) { MaterialTheme(colorScheme = Light, content = content) }