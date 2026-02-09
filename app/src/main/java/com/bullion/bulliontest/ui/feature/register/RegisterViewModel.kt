package com.bullion.bulliontest.ui.feature.register

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.bullion.bulliontest.core.util.GenderEnum
import com.bullion.bulliontest.core.util.StringUtil.validateConfirmPassword
import com.bullion.bulliontest.core.util.StringUtil.validateEmail
import com.bullion.bulliontest.core.util.StringUtil.validatePasswordRegister
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
            current.copy(
                phoneNumber = newValue,
                phoneNumberTouched = true,
                phoneNumberError = err
            )
        }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { current ->
            val err = validatePasswordRegister(newValue)
            current.copy(
                password = newValue,
                passwordTouched = true,
                passwordError = err
            )
        }
    }

    fun onPasswordVisibleChange() {
        _uiState.update { current ->
            current.copy(
                passwordVisible = !current.passwordVisible
            )
        }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { current ->
            val err = validateConfirmPassword(
                password = current.password,
                confirmPassword = newValue
            )
            current.copy(
                confirmPassword = newValue,
                confirmPasswordTouched = true,
                confirmPasswordError = err
            )
        }
    }

    fun onConfirmPasswordVisibleChange() {
        _uiState.update { current ->
            current.copy(
                confirmPasswordVisible = !current.passwordVisible
            )
        }
    }

    fun onDateOfBirthChange(newValue: String) {
        _uiState.update { current ->
            val err = if(newValue.isEmpty()) "Date of birth is required" else null
            current.copy(
                dateOfBirth = newValue,
                dateOfBirthTouched = true,
                dateOfBirthError = err
            )
        }
    }

    fun onDateOfBirthClick(newValue: Boolean) {
        _uiState.update { current ->
            current.copy(
                showDialogDate = newValue
            )
        }
    }

    fun onPhotoChange(newValue: String) {
        _uiState.update { current ->
            val err = if(newValue.isEmpty()) "Photo profile is required" else null
            current.copy(
                photo = newValue,
                photoTouched = true,
                photoError = err
            )
        }
    }

    fun onPhotoPicked(newValue: Uri) {
        _uiState.update { current ->
            current.copy(
                photoUri = newValue
            )
        }
    }
}