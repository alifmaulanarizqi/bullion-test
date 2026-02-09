package com.bullion.bulliontest.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bullion.bulliontest.core.util.Constant.LOGIN
import com.bullion.bulliontest.ui.feature.login.LoginScreen

fun NavGraphBuilder.loginComposable(
    onNavigateToRegister: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    composable(LOGIN) {
        LoginScreen(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToDashboard = onNavigateToDashboard,
        )
    }
}