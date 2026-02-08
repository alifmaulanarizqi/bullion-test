package com.bullion.bulliontest.ui.feature.login

data class LoginState(
    val email: String = "",
    val password: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,

    val emailTouched: Boolean = false,
    val passwordTouched: Boolean = false,

    val isLoading: Boolean = false,
) {
    val canSubmit: Boolean
        get() = emailError == null && passwordError == null &&
                email.isNotBlank() && password.isNotBlank()
}

sealed interface LoginEvent {
    data class ShowError(val message: String) : LoginEvent
    object Success : LoginEvent
}