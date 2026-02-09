package com.bullion.bulliontest.ui.feature.login

import androidx.lifecycle.ViewModel
import com.bullion.bulliontest.core.util.StringUtil.validateEmail
import com.bullion.bulliontest.core.util.StringUtil.validatePassword
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

    fun submit() {
        val state = _uiState.value
        if (!state.canSubmit) return
        // todo call login api
    }
}