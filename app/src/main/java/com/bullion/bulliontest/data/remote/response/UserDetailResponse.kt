package com.bullion.bulliontest.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("date_of_birth")
    val dateOfBirth: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("photo")
    val photo: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("address")
    val address: String? = null
)