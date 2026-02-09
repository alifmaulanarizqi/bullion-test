package com.bullion.bulliontest.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bullion.bulliontest.core.util.Constant.DASHBOARD
import com.bullion.bulliontest.core.util.Constant.LOGIN
import com.bullion.bulliontest.core.util.Constant.REGISTER
import com.bullion.bulliontest.core.util.Constant.SPLASH

class Screens(navHostController: NavHostController) {
    val login: () -> Unit = {
        navHostController.navigate(LOGIN) {
            popUpTo(SPLASH) { inclusive = true }
        }
    }

    val register: () -> Unit = {
        navHostController.navigate(REGISTER)
    }

    val backFromRegister: () -> Unit = {
        navHostController.popBackStack()
    }

    val dashboard: () -> Unit = {
        navHostController.navigate(DASHBOARD) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}