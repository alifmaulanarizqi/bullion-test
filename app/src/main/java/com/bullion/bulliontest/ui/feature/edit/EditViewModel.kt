package com.bullion.bulliontest.ui.feature.edit

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bullion.bulliontest.core.util.GenderEnum
import com.bullion.bulliontest.core.util.StringUtil.validateConfirmPassword
import com.bullion.bulliontest.core.util.StringUtil.validateEmail
import com.bullion.bulliontest.core.util.StringUtil.validatePasswordRegister
import com.bullion.bulliontest.data.remote.request.UserRequest
import com.bullion.bulliontest.data.remote.request.uriToMultipartPart
import com.bullion.bulliontest.data.repository.UserRepository
import com.bullion.bulliontest.domain.model.ApiErrorException
import com.bullion.bulliontest.domain.model.UserDetail
import com.bullion.bulliontest.ui.feature.register.RegisterEvent
import com.bullion.bulliontest.ui.feature.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(EditState())
    val uiState: StateFlow<EditState> = _uiState

    private val _event = MutableSharedFlow<EditEvent>()
    val event: SharedFlow<EditEvent> = _event

    private var userId: String = ""

    fun initializeUserData(userDetail: UserDetail) {
        userId = userDetail.id
        _uiState.update { current ->
            current.copy(
                firstName = userDetail.firstName,
                lastName = userDetail.lastName,
                email = userDetail.email,
                gender = when(userDetail.gender.uppercase()) {
                    "MALE" -> GenderEnum.Male
                    "FEMALE" -> GenderEnum.Female
                    else -> null
                },
                dateOfBirth = userDetail.dateOfBirth,
                phoneNumber = userDetail.phone,
                address = userDetail.address
            )
        }
    }

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

    fun submit() {
        val state = _uiState.value
        if (!state.canSubmit) {
            return
        }

        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(
                    isLoading = true
                )
            }

            try {
                val request = UserRequest(
                    firstName = state.firstName,
                    lastName = state.lastName,
                    gender = state.gender?.value ?: "",
                    dateOfBirth = state.dateOfBirth,
                    email = state.email,
                    phone = state.phoneNumber,
                    address = state.address
                )
                // Call edit API
                val result = userRepository.updateUser(
                    id = userId,
                    body = request
                )

                _uiState.update { current ->
                    current.copy(
                        isLoading = false
                    )
                }

                _event.emit(EditEvent.Success("Berhasil mengedit user"))
            } catch (e: Exception) {
                Log.d("waduh", "${e.message}")
                _uiState.update { current ->
                    current.copy(isLoading = false)
                }

                val message = when (e) {
                    is ApiErrorException -> e.message
                    else -> e.localizedMessage ?: "Unknown error"
                }

                _event.emit(EditEvent.ShowError(message))
            }
        }
    }
}