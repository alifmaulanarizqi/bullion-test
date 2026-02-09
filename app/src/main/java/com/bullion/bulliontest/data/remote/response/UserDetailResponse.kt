package com.bullion.bulliontest.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("date_of_birth")
    val dateOfBirth: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("address")
    val address: String
)