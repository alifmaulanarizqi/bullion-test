package com.bullion.bulliontest.ui.feature.register

import android.net.Uri
import com.bullion.bulliontest.core.util.GenderEnum

data class RegisterState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: GenderEnum? = null,
    val dateOfBirth: String = "",
    val showDialogDate: Boolean = false,
    val phoneNumber: String = "",
    val photo: String = "",
    val photoUri: Uri? = null,
    val password: String = "",
    val passwordVisible: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordVisible: Boolean = false,

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val genderError: String? = null,
    val dateOfBirthError: String? = null,
    val phoneNumberError: String? = null,
    val photoError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val firstNameTouched: Boolean = false,
    val lastNameTouched: Boolean = false,
    val emailTouched: Boolean = false,
    val genderTouched: Boolean = false,
    val dateOfBirthTouched: Boolean = false,
    val phoneNumberTouched: Boolean = false,
    val photoTouched: Boolean = false,
    val passwordTouched: Boolean = false,
    val confirmPasswordTouched: Boolean = false,

    val isLoading: Boolean = false,
) {
    val canSubmit: Boolean
        get() = emailError == null && passwordError == null &&
                firstNameError == null && lastNameError == null && genderError == null &&
                dateOfBirthError == null && phoneNumberError == null &&
                photoError == null && confirmPasswordError == null &&
                email.isNotBlank() && password.isNotBlank() &&
                firstName.isNotBlank() && lastName.isNotBlank() && gender != null &&
                dateOfBirth.isNotBlank() && phoneNumber.isNotBlank() &&
                photo.isNotBlank() && confirmPassword.isNotBlank()
}

sealed interface RegisterEvent {
    data class ShowError(val message: String) : RegisterEvent
    object Success : RegisterEvent
}