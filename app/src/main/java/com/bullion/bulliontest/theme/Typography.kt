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
        color = FontColorBlack33
    ),
    titleMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 22.sp,
        color = FontColorBlack33
    ),
    titleSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 20.sp,
        color = FontColorBlack33
    ),
    bodyLarge = TextStyle(
        fontFamily = poppins,
        fontSize = 18.sp,
        color = FontColorBlack33
    ),
    bodyMedium = TextStyle(
        fontFamily = poppins,
        fontSize = 16.sp,
        color = FontColorBlack33
    ),
    bodySmall = TextStyle(
        fontFamily = poppins,
        fontSize = 14.sp,
        color = FontColorBlack33
    ),
    labelSmall = TextStyle(
        fontFamily = poppins,
        fontSize = 12.sp,
        color = FontColorBlack33
    ),
)