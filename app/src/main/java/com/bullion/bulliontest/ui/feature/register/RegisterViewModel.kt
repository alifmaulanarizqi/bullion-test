package com.bullion.bulliontest.ui.feature.register

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bullion.bulliontest.core.util.GenderEnum
import com.bullion.bulliontest.core.util.StringUtil.validateConfirmPassword
import com.bullion.bulliontest.core.util.StringUtil.validateEmail
import com.bullion.bulliontest.core.util.StringUtil.validatePasswordRegister
import com.bullion.bulliontest.data.remote.request.uriToMultipartPart
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
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val application: Application
): ViewModel() {
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
            val err = if(newValue == null) "Gender is required" else null
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

    fun onAddressChange(newValue: String) {
        _uiState.update { current ->
            val err = if(newValue.isEmpty()) "Address is required" else null
            current.copy(
                address = newValue,
                addressTouched = true,
                addressError = err
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

    fun submit() {
        val state = _uiState.value
        if (!state.canSubmit) {
            return
        }

        if (state.photoUri == null) {
            viewModelScope.launch {
                _event.emit(RegisterEvent.ShowError("Please select a photo"))
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(
                    isLoading = true
                )
            }

            try {
                val photoPart = uriToMultipartPart(
                    context = application,
                    uri = state.photoUri,
                    formName = "photo"
                )

                // Call register API
                val result = userRepository.register(
                    firstName = state.firstName,
                    lastName = state.lastName,
                    gender = state.gender?.value ?: "",
                    dateOfBirth = state.dateOfBirth,
                    email = state.email,
                    phone = state.phoneNumber,
                    address = state.address,
                    photo = photoPart,
                    password = state.password
                )

                _uiState.update { current ->
                    current.copy(
                        isLoading = false
                    )
                }

                _event.emit(RegisterEvent.Success("Berhasil menambahkan user"))
            } catch (e: Exception) {
                _uiState.update { current ->
                    current.copy(isLoading = false)
                }

                val message = when (e) {
                    is ApiErrorException -> e.message
                    else -> e.localizedMessage ?: "Unknown error"
                }

                _event.emit(RegisterEvent.ShowError(message))
            }
        }
    }
}