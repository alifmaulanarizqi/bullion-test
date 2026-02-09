package com.bullion.bulliontest.ui.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bullion.bulliontest.core.util.StringUtil.validateEmail
import com.bullion.bulliontest.core.util.StringUtil.validatePassword
import com.bullion.bulliontest.data.local.SessionManager
import com.bullion.bulliontest.data.remote.request.LoginRequest
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
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
): ViewModel() {
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

        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(
                    isLoading = true
                )
            }

            val request = LoginRequest(
                email = state.email,
                password = state.password
            )

            try {
                val login = userRepository.login(request)
                sessionManager.saveToken(login.token)

                _uiState.update { current ->
                    current.copy(
                        isLoading = false
                    )
                }

                _event.emit(LoginEvent.Success)
            } catch (e: Exception) {
                _uiState.update { current ->
                    current.copy(isLoading = false)
                }

                val message = when (e) {
                    is ApiErrorException -> e.message
                    else -> e.localizedMessage ?: "Unknown error"
                }

                _event.emit(LoginEvent.ShowError(message))
            }
        }
    }
}