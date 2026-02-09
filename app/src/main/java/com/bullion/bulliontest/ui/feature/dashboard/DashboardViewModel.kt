package com.bullion.bulliontest.ui.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bullion.bulliontest.data.repository.UserRepository
import com.bullion.bulliontest.domain.model.ApiErrorException
import com.bullion.bulliontest.domain.model.Banner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.bullion.bulliontest.R
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState

    private val _event = MutableSharedFlow<DashboardEvent>()
    val event: SharedFlow<DashboardEvent> = _event

    private val _banners = MutableStateFlow(
        listOf(
            Banner(R.drawable.banner),
            Banner(R.drawable.banner),
            Banner(R.drawable.banner),
        )
    )
    val banners: StateFlow<List<Banner>> = _banners.asStateFlow()

    companion object {
        private const val PAGE_SIZE = 10
    }

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            val currentState = _uiState.value

            // Prevent multiple simultaneous loads
            if (currentState.isLoading) {
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Calculate offset based on current page
                val currentPage = currentState.currentPage
                val offset = currentPage * PAGE_SIZE

                val users = userRepository.getListUser(offset = offset, limit = PAGE_SIZE)

                _uiState.update { current ->
                    val newUsers = current.users + users
                    val newPage = currentPage + 1
                    val hasMore = users.size >= PAGE_SIZE

                    current.copy(
                        users = newUsers,
                        isLoading = false,
                        error = null,
                        currentPage = newPage,
                        hasMorePages = hasMore
                    )
                }
            } catch (e: Exception) {
                val message = when (e) {
                    is ApiErrorException -> e.message
                    else -> e.localizedMessage ?: "Failed to load users"
                }

                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        error = message
                    )
                }

                _event.emit(DashboardEvent.ShowError(message))
            }
        }
    }

    fun loadMoreUsers() {
        if (_uiState.value.hasMorePages && !_uiState.value.isLoading) {
            loadUsers()
        }
    }


    fun onChangeShowDetailDialog(newValue: Boolean) {
        _uiState.update { it.copy(showDetailDialog = newValue) }
    }

    fun onUserClick(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingDetail = true) }

            try {
                val userDetail = userRepository.getDetailUser(userId)

                _uiState.update {
                    it.copy(
                        selectedUser = userDetail,
                        isLoadingDetail = false,
                        showDetailDialog = true
                    )
                }
            } catch (e: Exception) {
                val message = when (e) {
                    is ApiErrorException -> e.message
                    else -> e.localizedMessage ?: "Failed to load user detail"
                }

                _uiState.update { it.copy(isLoadingDetail = false) }
                _event.emit(DashboardEvent.ShowError(message))
            }
        }
    }

    fun dismissDetailDialog() {
        _uiState.update {
            it.copy(
                showDetailDialog = false,
                selectedUser = null
            )
        }
    }
}
