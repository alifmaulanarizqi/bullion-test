package com.bullion.bulliontest.ui.feature.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bullion.bulliontest.core.util.DateUtil
import com.bullion.bulliontest.domain.model.User
import com.bullion.bulliontest.theme.AppTypography
import com.bullion.bulliontest.theme.Black
import com.bullion.bulliontest.theme.Black03
import com.bullion.bulliontest.theme.Blue92
import com.bullion.bulliontest.theme.GradientBackground
import com.bullion.bulliontest.theme.GradientText
import com.bullion.bulliontest.theme.Gray5D
import com.bullion.bulliontest.theme.Gray93
import com.bullion.bulliontest.theme.Orange2A
import com.bullion.bulliontest.theme.White
import com.bullion.bulliontest.theme.dimension10
import com.bullion.bulliontest.theme.dimension16
import com.bullion.bulliontest.theme.dimension2
import com.bullion.bulliontest.theme.dimension20
import com.bullion.bulliontest.theme.dimension24
import com.bullion.bulliontest.theme.dimension28
import com.bullion.bulliontest.theme.dimension32
import com.bullion.bulliontest.theme.dimension6
import com.bullion.bulliontest.theme.dimension8
import com.bullion.bulliontest.ui.common.CommonBase64Image
import com.bullion.bulliontest.ui.common.CommonFilledButton

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is DashboardEvent.ShowError -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    // Infinite scroll - detect when user scrolls near the end
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            Triple(lastVisibleItemIndex, totalItems, lastVisibleItemIndex >= totalItems - 3)
        }.collect { (lastVisibleItemIndex, totalItems, shouldTrigger) ->
            if (totalItems > 0 && shouldTrigger) {
                viewModel.loadMoreUsers()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = White,
                    contentColor = Black03,
                    actionColor = Gray93
                )
            }
        },
        floatingActionButton = {
            if (!state.isLoading && !state.isRefreshing) {
                FloatingActionButton(
                    onClick = { viewModel.refresh() },
                    containerColor = Orange2A,
                    contentColor = White
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(GradientBackground)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimension24)
                        .weight(0.35f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                }
                Surface(
                    shape = RoundedCornerShape(topStart = dimension28, topEnd = dimension28),
                    color = Color.White,
                    shadowElevation = dimension8,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(dimension32))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = dimension24)
                        ) {
                            Text(
                                text = "User List",
                                style = AppTypography.bodySmall.copy(
                                    brush = GradientText,
                                    fontWeight = FontWeight.W500,
                                )
                            )
                        }
                        ListCard(
                            state = state,
                            listState = listState,
                            viewModel = viewModel,
                            onNavigateToRegister = onNavigateToRegister
                        )
                    }
                }
            }

            // Loading overlay saat fetch detail user
            if (state.isLoadingDetail) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(enabled = false) { },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Orange2A)
                }
            }

            // Detail dialog
            if (state.showDetailDialog && state.selectedUser != null) {
                DetailUserDialog(
                    user = state.selectedUser,
                    onDismiss = { viewModel.dismissDetailDialog() },
                    onEdit = { }
                )
            }
        }
    }
}

@Composable
private fun ListCard(
    state: DashboardState,
    listState: LazyListState,
    viewModel: DashboardViewModel,
    onNavigateToRegister: () -> Unit
) {
    when {
        state.isLoading && state.users.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Orange2A)
            }
        }
        state.isRefreshing && state.users.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Orange2A)
            }
        }
        state.error != null && state.users.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(dimension24)
                ) {
                    Text(
                        text = state.error,
                        style = AppTypography.bodyMedium.copy(color = Orange2A)
                    )
                }
            }
        }
        else -> {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(dimension16),
                verticalArrangement = Arrangement.spacedBy(dimension16)
            ) {
                // Refreshing indicator at top
                if (state.isRefreshing && state.users.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimension16),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = White,
                                modifier = Modifier.size(dimension24)
                            )
                        }
                    }
                }

                // list
                items(
                    items = state.users,
                    key = { it.id }
                ) { user ->
                    UserCard(
                        user = user,
                        onClick = {
                            viewModel.onUserClick(user.id)
                        }
                    )
                }

                // Loading indicator at bottom for pagination
                if (state.isLoading && state.users.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimension16),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    color = Orange2A,
                                    modifier = Modifier.size(dimension20)
                                )
                                Spacer(modifier = Modifier.width(dimension8))
                                Text(
                                    text = "Loading more...",
                                    style = AppTypography.bodySmall.copy(
                                        color = Orange2A
                                    )
                                )
                            }
                        }
                    }
                }

                // End of list indicator
                if (!state.hasMorePages && state.users.isNotEmpty() && !state.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimension16),
                            contentAlignment = Alignment.Center
                        ) {
                            CommonFilledButton(
                                text = "Add Users",
                                onClick = onNavigateToRegister,
                                buttonColor = Blue92,
                                textStyle = AppTypography.labelSmall.copy(
                                    fontWeight = FontWeight.W500,
                                    color = White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserCard(
    user: User,
    onClick: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClick(true)
            }),
        shape = RoundedCornerShape(dimension8),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimension2
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimension16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(dimension32)
                    .clip(RoundedCornerShape(dimension6)),
                contentAlignment = Alignment.Center
            ) {
                CommonBase64Image(
                    base64 = user.photo,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(dimension10))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.name,
                    style = AppTypography.labelSmall.copy(
                        fontWeight = FontWeight.W500,
                        color = Black
                    )
                )
                Spacer(modifier = Modifier.height(dimension2))
                Text(
                    text = user.email,
                    style = AppTypography.labelSmall.copy(
                        fontWeight = FontWeight.W500,
                        color = Gray5D
                    )
                )
            }
            Text(
                text = DateUtil.formatDateMonthDayYear(user.dateOfBirth),
                style = AppTypography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = Black03,
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}