package com.bullion.bulliontest.ui.feature.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event

    fun onEmailChange(newValue: String) {
        _uiState.update { current ->
            val err = validateEmail(newValue)
            current.copy(
                email = newValue,
                emailTouched = true,
                emailError = err
            )
        }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { current ->
            val err = validatePassword(newValue)
            current.copy(
                password = newValue,
                passwordTouched = true,
                passwordError = err
            )
        }
    }

    private fun validateEmail(value: String): String? {
        if (value.isBlank()) return "Email is required"
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return if (!emailRegex.matches(value)) "Email is not valid" else null
    }

    private fun validatePassword(value: String): String? {
        if (value.isBlank()) return "Password is required"
        if (value.length < 8) return "Minimum 8 characters"
        return null
    }

    fun submit() {
        val state = _uiState.value
        if (!state.canSubmit) return
        // todo call login api
    }
}