package com.bullion.bulliontest.domain.model

data class User (
    val id: String,
    val name: String,
    val gender: String,
    val dateOfBirth: String,
    val email: String,
    val photo: String,
    val phone: String,
    val address: String
)