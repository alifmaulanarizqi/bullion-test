package com.bullion.bulliontest.ui.feature.edit

import com.bullion.bulliontest.core.util.GenderEnum

data class EditState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: GenderEnum? = null,
    val dateOfBirth: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val showDialogDate: Boolean = false,
    val isLoading: Boolean = false,

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val genderError: String? = null,
    val dateOfBirthError: String? = null,
    val phoneNumberError: String? = null,
    val addressError: String? = null,

    val firstNameTouched: Boolean = false,
    val lastNameTouched: Boolean = false,
    val emailTouched: Boolean = false,
    val genderTouched: Boolean = false,
    val dateOfBirthTouched: Boolean = false,
    val phoneNumberTouched: Boolean = false,
    val addressTouched: Boolean = false,

    ) {
    val canSubmit: Boolean
        get() = emailError == null && firstNameError == null &&
                lastNameError == null && genderError == null &&
                dateOfBirthError == null && phoneNumberError == null &&
                addressError == null && email.isNotBlank() &&
                firstName.isNotBlank() && lastName.isNotBlank() && gender != null &&
                dateOfBirth.isNotBlank() && phoneNumber.isNotBlank() && address.isNotBlank()
}

sealed interface EditEvent {
    data class ShowError(val message: String) : EditEvent
    data class Success(val message: String) : EditEvent
}