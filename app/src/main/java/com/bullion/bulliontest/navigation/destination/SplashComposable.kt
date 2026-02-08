package com.bullion.bulliontest.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bullion.bulliontest.core.util.Constant.SPLASH
import com.bullion.bulliontest.feature.splash.SplashScreen

fun NavGraphBuilder.splashComposable(
    onNavigateToDashboard: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    composable(SPLASH) {
        SplashScreen(
            onNavigateToDashboard = onNavigateToDashboard,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}