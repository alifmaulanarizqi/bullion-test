package com.bullion.bulliontest.navigation

import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.bullion.bulliontest.core.util.Constant.DASHBOARD
import com.bullion.bulliontest.core.util.Constant.EDIT
import com.bullion.bulliontest.core.util.Constant.LOGIN
import com.bullion.bulliontest.core.util.Constant.REGISTER
import com.bullion.bulliontest.core.util.Constant.SPLASH
import com.bullion.bulliontest.domain.model.UserDetail
import com.google.gson.Gson

class Screens(navHostController: NavHostController) {
    val login: () -> Unit = {
        navHostController.navigate(LOGIN) {
            popUpTo(SPLASH) { inclusive = true }
        }
    }

    val register: () -> Unit = {
        navHostController.navigate(REGISTER)
    }

    val backPop: () -> Unit = {
        navHostController.popBackStack()
    }

    val backPopWithRefresh: () -> Unit = {
        navHostController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("refresh", true)
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

    val editUser: (UserDetail) -> Unit = { userDetail ->
        val gson = Gson()
        val userJson = Uri.encode(gson.toJson(userDetail))
        navHostController.navigate("$EDIT/$userJson")
    }

}