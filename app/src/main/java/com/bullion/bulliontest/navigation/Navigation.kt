package com.bullion.bulliontest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bullion.bulliontest.core.util.Constant.SPLASH
import com.bullion.bulliontest.navigation.destination.dashboardComposable
import com.bullion.bulliontest.navigation.destination.dashboardComposable
import com.bullion.bulliontest.navigation.destination.editComposable
import com.bullion.bulliontest.navigation.destination.loginComposable
import com.bullion.bulliontest.navigation.destination.registerComposable
import com.bullion.bulliontest.navigation.destination.splashComposable

@Composable
fun Navigation(
    navHostController: NavHostController,
) {
    val screen = remember(navHostController) {
        Screens(navHostController)
    }

    NavHost(
        navHostController,
        SPLASH
    ) {
        splashComposable(
            onNavigateToDashboard = screen.dashboard,
            onNavigateToLogin = screen.login
        )
        loginComposable(
            onNavigateToRegister = screen.register,
            onNavigateToDashboard = screen.dashboard
        )
        registerComposable(
            onBack = screen.backPop
        )
        dashboardComposable(
            onNavigateToRegister= screen.register,
            onNavigateToEdit = screen.editUser
        )
        editComposable(
            onBack = screen.backPop
        )
    }
}