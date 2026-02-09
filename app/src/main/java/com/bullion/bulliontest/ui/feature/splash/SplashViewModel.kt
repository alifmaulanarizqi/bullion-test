package com.bullion.bulliontest.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bullion.bulliontest.data.local.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {
    fun redirectTo(
        onNavigateToDashboard: () -> Unit,
        onNavigateToLogin: () -> Unit,
    ) {
        viewModelScope.launch {
            val token = sessionManager.getToken()
            if (!token.isNullOrBlank()) {
                onNavigateToDashboard()
            } else {
                onNavigateToLogin()
            }
        }
    }
}