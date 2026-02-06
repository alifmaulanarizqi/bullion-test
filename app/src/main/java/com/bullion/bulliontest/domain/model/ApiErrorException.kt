package com.bullion.bulliontest.domain.model

class ApiErrorException(
    val errorCode: String?,
    override val message: String,
    val messageEn: String? = null
) : Exception(message)