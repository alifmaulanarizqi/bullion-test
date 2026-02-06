package com.bullion.bulliontest.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("err_code")
    val errorCode: String,
    @SerializedName("err_message")
    val errorMessage: String,
    @SerializedName("err_message_en")
    val errorMessageEn: String,
)