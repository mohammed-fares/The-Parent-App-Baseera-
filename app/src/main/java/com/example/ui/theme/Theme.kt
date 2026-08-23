package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = BaseeraEmerald,
  onPrimary = Color(0xFF003822),
  primaryContainer = BaseeraEmeraldDark,
  onPrimaryContainer = Color(0xFFD1FAE5),
  secondary = BaseeraCyan,
  onSecondary = Color(0xFF00363D),
  secondaryContainer = Color(0xFF0E7490),
  onSecondaryContainer = Color(0xFFCFFAFE),
  tertiary = BaseeraGold,
  onTertiary = Color(0xFF452B00),
  tertiaryContainer = BaseeraGoldContainer,
  onTertiaryContainer = BaseeraGoldLight,
  error = BaseeraRed,
  onError = Color.White,
  errorContainer = BaseeraRedContainer,
  onErrorContainer = Color(0xFFFECACA),
  background = BaseeraNavyDark,
  onBackground = BaseeraTextPrimary,
  surface = BaseeraNavySurface,
  onSurface = BaseeraTextPrimary,
  surfaceVariant = BaseeraNavyCard,
  onSurfaceVariant = BaseeraTextSecondary,
  outline = BaseeraNavyBorder,
  outlineVariant = Color(0xFF1E3A5F)
)

private val LightColorScheme = lightColorScheme(
  primary = Color(0xFF0F172A),
  onPrimary = Color.White,
  primaryContainer = Color(0xFF1E293B),
  onPrimaryContainer = Color(0xFFF8FAFC),
  secondary = BaseeraEmeraldDark,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFD1FAE5),
  onSecondaryContainer = Color(0xFF065F46),
  tertiary = BaseeraGoldDark,
  onTertiary = Color.White,
  tertiaryContainer = Color(0xFFFEF3C7),
  onTertiaryContainer = Color(0xFF92400E),
  error = BaseeraRed,
  onError = Color.White,
  errorContainer = Color(0xFFFEE2E2),
  onErrorContainer = Color(0xFF991B1B),
  background = BaseeraLightBackground,
  onBackground = BaseeraLightTextPrimary,
  surface = BaseeraLightSurface,
  onSurface = BaseeraLightTextPrimary,
  surfaceVariant = BaseeraLightCard,
  onSurfaceVariant = BaseeraLightTextSecondary,
  outline = BaseeraLightBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to sleek Navy Dark mode as specified in prompt
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
