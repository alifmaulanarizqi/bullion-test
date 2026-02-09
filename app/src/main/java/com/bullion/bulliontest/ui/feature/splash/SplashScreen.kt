package com.bullion.bulliontest.ui.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.bullion.bulliontest.core.util.DisplaySvgRawFile
import com.bullion.bulliontest.theme.dimension20
import kotlinx.coroutines.delay
import com.bullion.bulliontest.R
import com.bullion.bulliontest.theme.GradientBackground

@Composable
fun SplashScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        delay(2000)
        viewModel.redirectToCashierPage(
            onNavigateToDashboard = onNavigateToDashboard,
            onNavigateToLogin = onNavigateToLogin
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientBackground)
            .padding(dimension20),
        contentAlignment = Alignment.Center
    ) {
        DisplaySvgRawFile(
            model = R.raw.bullion_logo,
            contentDescription = "Bullion Logo",
            width = 250.dp,
            height = 250.dp
        )
    }
}