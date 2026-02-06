package com.bullion.bulliontest.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bullion.bulliontest.core.util.Constant.LOGIN

fun NavGraphBuilder.loginComposable() {
    composable(LOGIN) {
        LoginScreen()
    }
}