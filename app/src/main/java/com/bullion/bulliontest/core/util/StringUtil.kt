package com.bullion.bulliontest.core.util

object StringUtil {
    fun validateEmail(value: String): String? {
        if (value.isBlank()) return "Email is required"
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return if (!emailRegex.matches(value)) "Email is not valid" else null
    }

    fun validatePassword(value: String): String? {
        if (value.isBlank()) return "Password is required"
        if (value.length < 8) return "Minimum 8 characters"
        return null
    }

    fun validatePasswordRegister(value: String): String? {
        if (value.isBlank()) return "Password is required"
        if (value.length < 8) return "Minimum 8 characters"

        val hasUppercase = value.any { it.isUpperCase() }
        val hasNumber = value.any { it.isDigit() }

        if (!hasUppercase || !hasNumber) {
            return "Min 1 capital letter and 1 number"
        }

        return null
    }

    fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): String? {
        if (confirmPassword.isBlank()) return "Confirm password is required"
        if (confirmPassword != password) return "Confirm password must match password"
        return null
    }
}