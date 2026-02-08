package com.bullion.bulliontest.navigation

import androidx.navigation.NavHostController
import com.bullion.bulliontest.core.util.Constant.LOGIN
import com.bullion.bulliontest.core.util.Constant.SPLASH

class Screens(navHostController: NavHostController) {
    val login: () -> Unit = {
        navHostController.navigate(LOGIN) {
            popUpTo(SPLASH) { inclusive = true }
        }
    }
}