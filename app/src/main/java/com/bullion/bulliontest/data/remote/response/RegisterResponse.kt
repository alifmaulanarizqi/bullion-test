package com.bullion.bulliontest.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
)