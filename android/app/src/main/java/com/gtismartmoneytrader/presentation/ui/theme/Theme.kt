package com.gtismartmoneytrader.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2196F3),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1976D2),
    secondary = Color(0xFFFFD700),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFFB3B3B3),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

object GTIColors {
    val Yellow = Color(0xFFFFD700)
    val Blue = Color(0xFF2196F3)
    val Black = Color(0xFF1A1A1A)
    
    val BuyCall = Color(0xFF4CAF50)
    val BuyPut = Color(0xFFF44336)
    
    val ConfidenceHigh = Color(0xFF4CAF50)
    val ConfidenceMedium = Color(0xFFFFC107)
    val ConfidenceLow = Color(0xFFF44336)
    
    val Trending = Color(0xFF4CAF50)
    val Sideways = Color(0xFFFFC107)
    
    val ChartBackground = Color(0xFF1A1A1A)
    val ChartGrid = Color(0xFF333333)
    val ChartText = Color(0xFF808080)
    val CandleWick = Color(0xFFCCCCCC)
}

@Composable
fun GTITheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
