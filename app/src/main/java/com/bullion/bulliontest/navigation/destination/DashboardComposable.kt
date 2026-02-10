package com.bullion.bulliontest.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bullion.bulliontest.core.util.Constant.DASHBOARD
import com.bullion.bulliontest.domain.model.UserDetail
import com.bullion.bulliontest.ui.feature.dashboard.DashboardScreen

fun NavGraphBuilder.dashboardComposable(
    onNavigateToRegister: () -> Unit,
    onNavigateToEdit: (user: UserDetail) -> Unit,
    onRefreshRequired: () -> Boolean,
    onRefreshHandled: () -> Unit,
) {
    composable(DASHBOARD) {
        DashboardScreen(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToEdit = onNavigateToEdit,
            shouldRefresh = onRefreshRequired(),
            onRefreshHandled = onRefreshHandled
        )
    }
}