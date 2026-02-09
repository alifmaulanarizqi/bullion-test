package com.bullion.bulliontest.ui.feature.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bullion.bulliontest.data.repository.UserRepository
import com.bullion.bulliontest.domain.model.ApiErrorException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState

    private val _event = MutableSharedFlow<DashboardEvent>()
    val event: SharedFlow<DashboardEvent> = _event

    companion object {
        private const val PAGE_SIZE = 10
        private const val TAG = "DashboardViewModel"
    }

    init {
        Log.d(TAG, "ViewModel initialized, loading first page")
        loadUsers()
    }

    fun loadUsers(isRefresh: Boolean = false) {
        viewModelScope.launch {
            val currentState = _uiState.value
            Log.d(TAG, "loadUsers called - isRefresh: $isRefresh, currentPage: ${currentState.currentPage}, isLoading: ${currentState.isLoading}, hasMorePages: ${currentState.hasMorePages}")

            // Prevent multiple simultaneous loads
            if (!isRefresh && currentState.isLoading) {
                Log.d(TAG, "Already loading, skipping")
                return@launch
            }
            if (isRefresh && currentState.isRefreshing) {
                Log.d(TAG, "Already refreshing, skipping")
                return@launch
            }

            // Reset state on refresh
            if (isRefresh) {
                _uiState.update { it.copy(isRefreshing = true, error = null, currentPage = 0) }
            } else {
                _uiState.update { it.copy(isLoading = true, error = null) }
            }

            try {
                // Calculate offset based on current page
                val currentPage = if (isRefresh) 0 else currentState.currentPage
                val offset = currentPage * PAGE_SIZE

                Log.d(TAG, "Fetching users - offset: $offset, limit: $PAGE_SIZE")

                val users = userRepository.getListUser(offset = offset, limit = PAGE_SIZE)

                Log.d(TAG, "Received ${users.size} users")

                _uiState.update { current ->
                    val newUsers = if (isRefresh) users else current.users + users
                    val newPage = currentPage + 1
                    val hasMore = users.size >= PAGE_SIZE

                    Log.d(TAG, "Updating state - totalUsers: ${newUsers.size}, newPage: $newPage, hasMorePages: $hasMore")

                    current.copy(
                        users = newUsers,
                        isLoading = false,
                        isRefreshing = false,
                        error = null,
                        currentPage = newPage,
                        hasMorePages = hasMore
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading users", e)
                val message = when (e) {
                    is ApiErrorException -> e.message
                    else -> e.localizedMessage ?: "Failed to load users"
                }

                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = message
                    )
                }

                _event.emit(DashboardEvent.ShowError(message))
            }
        }
    }

    fun loadMoreUsers() {
        Log.d(TAG, "loadMoreUsers called")
        if (_uiState.value.hasMorePages && !_uiState.value.isLoading) {
            Log.d(TAG, "Triggering load more")
            loadUsers(isRefresh = false)
        } else {
            Log.d(TAG, "Cannot load more - hasMorePages: ${_uiState.value.hasMorePages}, isLoading: ${_uiState.value.isLoading}")
        }
    }

    fun refresh() {
        Log.d(TAG, "refresh called")
        loadUsers(isRefresh = true)
    }
}

