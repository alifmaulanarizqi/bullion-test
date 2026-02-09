package com.bullion.bulliontest.ui.feature.dashboard

import com.bullion.bulliontest.domain.model.User
import com.bullion.bulliontest.domain.model.UserDetail

data class DashboardState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 0,
    val hasMorePages: Boolean = true,
    val showDetailDialog: Boolean = false,
    val selectedUser: UserDetail? = null,
    val isLoadingDetail: Boolean = false,
)

sealed interface DashboardEvent {
    data class ShowError(val message: String) : DashboardEvent
}

