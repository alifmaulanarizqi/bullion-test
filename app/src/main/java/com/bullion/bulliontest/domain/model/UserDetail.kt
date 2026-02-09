package com.bullion.bulliontest.domain.model

data class UserDetail (
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val dateOfBirth: String,
    val email: String,
    val photo: String,
    val phone: String,
    val address: String
)