package com.bullion.bulliontest.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {
    fun redirectToCashierPage(
        onNavigateToDashboard: () -> Unit,
        onNavigateToLogin: () -> Unit,
    ) {
        viewModelScope.launch {
            onNavigateToLogin()
        }
    }
}