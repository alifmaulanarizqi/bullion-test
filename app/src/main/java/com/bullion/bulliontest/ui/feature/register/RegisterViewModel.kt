package com.bullion.bulliontest.ui.feature.register

import androidx.lifecycle.ViewModel
import com.bullion.bulliontest.core.util.GenderEnum
import com.bullion.bulliontest.core.util.StringUtil.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState

    private val _event = MutableSharedFlow<RegisterEvent>()
    val event: SharedFlow<RegisterEvent> = _event

    fun onFirstNameChange(newValue: String) {
        _uiState.update { current ->
            val err = if(newValue.isEmpty()) "First name is required" else null
            current.copy()
            current.copy(
                firstName = newValue,
                firstNameTouched = true,
                firstNameError = err
            )
        }
    }

    fun onLastNameChange(newValue: String) {
        _uiState.update { current ->
            val err = if(newValue.isEmpty()) "Last name is required" else null
            current.copy()
            current.copy(
                lastName = newValue,
                lastNameTouched = true,
                lastNameError = err
            )
        }
    }

    fun onGenderChange(newValue: GenderEnum?) {
        _uiState.update { current ->
            val err = if(newValue != null) "Gender is required" else null
            current.copy()
            current.copy(
                gender = newValue,
                genderTouched = true,
                genderError = err
            )
        }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { current ->
            val err = validateEmail(newValue)
            current.copy()
            current.copy(
                email = newValue,
                emailTouched = true,
                emailError = err
            )
        }
    }

    fun onPhoneNumberChange(newValue: String) {
        _uiState.update { current ->
            val err = if(newValue.isEmpty()) "Phone number is required" else null
            current.copy()
            current.copy(
                phoneNumber = newValue,
                phoneNumberTouched = true,
                phoneNumberError = err
            )
        }
    }
}