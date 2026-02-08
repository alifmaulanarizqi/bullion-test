package com.bullion.bulliontest.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bullion.bulliontest.core.util.Constant.REGISTER
import com.bullion.bulliontest.ui.feature.register.RegisterScreen

fun NavGraphBuilder.registerComposable(
    onBack: () -> Unit
) {
    composable(REGISTER) {
        RegisterScreen(
            onBack = onBack
        )
    }
}