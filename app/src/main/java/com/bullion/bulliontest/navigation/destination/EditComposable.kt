package com.bullion.bulliontest.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bullion.bulliontest.core.util.Constant.EDIT
import com.bullion.bulliontest.domain.model.UserDetail
import com.bullion.bulliontest.ui.feature.edit.EditScreen
import com.google.gson.Gson

fun NavGraphBuilder.editComposable(
    onBack: () -> Unit
) {
    composable(
        route = "$EDIT/{userDetailJson}",
        arguments = listOf(
            navArgument("userDetailJson") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val userDetailJson = backStackEntry.arguments?.getString("userDetailJson") ?: ""
        val gson = Gson()
        val userDetail = gson.fromJson(userDetailJson, UserDetail::class.java)

        EditScreen(
            userDetail = userDetail,
            onBack = onBack
        )
    }
}

