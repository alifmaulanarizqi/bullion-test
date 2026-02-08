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
}