package com.bullion.bulliontest.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(
    @SerializedName("status")
    val status: String,
    @SerializedName("iserror")
    val isError: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T?
)