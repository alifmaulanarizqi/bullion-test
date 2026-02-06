package com.bullion.bulliontest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bullion.bulliontest.core.util.Constant.LOGIN
import com.bullion.bulliontest.navigation.destination.loginComposable

@Composable
fun Navigation(
    navHostController: NavHostController,
) {
    val screen = remember(navHostController) {
        Screens(navHostController)
    }

    NavHost(
        navHostController,
        LOGIN
    ) {
        loginComposable()
    }
}