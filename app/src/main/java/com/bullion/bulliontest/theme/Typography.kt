package com.bullion.bulliontest.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bullion.bulliontest.R


private val poppins = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold),
)

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 28.sp,
        color = Black
    ),
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 22.sp,
        color = Black
    ),
    titleSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 20.sp,
        color = Black
    ),
    bodyLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 18.sp,
        color = Black
    ),
    bodyMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 16.sp,
        color = Black
    ),
    bodySmall = TextStyle(
        fontFamily = poppins,
        fontSize = 14.sp,
        color = Black
    ),
    labelSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 12.sp,
        color = Black
    ),
)