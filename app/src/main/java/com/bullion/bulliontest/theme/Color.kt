package com.bullion.bulliontest.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Brown1A = Color(0xFF91361A)
val Orange2A = Color(0xFFF05A2A)
val Orange76 = Color(0xFFF89576)
val Blue92 = Color(0xFF255E92)
val Black = Color(0xFF000000)
val Black03 = Color(0xFF030303)
val White = Color(0xFFFFFFFF)
val Gray5D = Color(0xFF5D5D5D)
val GrayB2 = Color(0xFFAEAEB2)
val GradientBackground = Brush.linearGradient(
    colors = listOf(
        Orange76,
        Orange2A,
        Orange76,
    ),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)

val GradientText = Brush.linearGradient(
    colors = listOf(
        Brown1A,
        Orange2A,
        Orange76,
    ),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)