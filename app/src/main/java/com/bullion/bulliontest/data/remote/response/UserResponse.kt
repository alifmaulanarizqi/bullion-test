package com.bullion.bulliontest.data.remote.response

import android.util.Base64
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("date_of_birth")
    val dateOfBirth: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("photo")
    val photoBase64: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("address")
    val address: String
)

fun UserResponse.photoBytes(): ByteArray =
    Base64.decode(photoBase64, Base64.DEFAULT)