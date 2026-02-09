package com.bullion.bulliontest.domain.repository

import com.bullion.bulliontest.data.remote.datasource.UserDataSource
import com.bullion.bulliontest.data.remote.request.LoginRequest
import com.bullion.bulliontest.data.remote.request.UserRequest
import com.bullion.bulliontest.data.remote.response.ErrorResponse
import com.bullion.bulliontest.data.repository.UserRepository
import com.bullion.bulliontest.domain.mapper.toDomain
import com.bullion.bulliontest.domain.model.ApiErrorException
import com.bullion.bulliontest.domain.model.Login
import com.bullion.bulliontest.domain.model.Register
import com.bullion.bulliontest.domain.model.User
import com.bullion.bulliontest.domain.model.UserDetail
import com.google.gson.Gson
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remote: UserDataSource
) : UserRepository {
    private val gson = Gson()

    override suspend fun login(users: LoginRequest): Login {
        val result = remote.login(users)

        if (!result.isSuccessful) {
            val errStr = result.errorBody()?.string()
            val err = errStr?.let {
                runCatching {
                    gson.fromJson(it, ErrorResponse::class.java)
                }.getOrNull()
            }

            throw ApiErrorException(
                errorCode = err?.errorCode ?: result.code().toString(),
                message = err?.errorMessage ?: "Request failed (${result.code()})",
                messageEn = err?.errorMessageEn
            )
        }

        return result.body()?.data?.toDomain()
            ?: throw ApiErrorException(null, "Data is null")
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        gender: String,
        dateOfBirth: String,
        email: String,
        phone: String,
        address: String,
        photo: MultipartBody.Part,
        password: String
    ): Register {
        val result = remote.register(
            firstName = firstName,
            lastName = lastName,
            gender = gender,
            dateOfBirth = dateOfBirth,
            email = email,
            phone = phone,
            address = address,
            photo = photo,
            password = password
        )

        if (!result.isSuccessful) {
            val errStr = result.errorBody()?.string()
            val err = errStr?.let {
                runCatching {
                    gson.fromJson(it, ErrorResponse::class.java)
                }.getOrNull()
            }

            throw ApiErrorException(
                errorCode = err?.errorCode ?: result.code().toString(),
                message = err?.errorMessage ?: "Request failed (${result.code()})",
                messageEn = err?.errorMessageEn
            )
        }

        return result.body()?.data?.toDomain()
            ?: throw ApiErrorException(null, "Data is null")
    }

    override suspend fun getListUser(offset: Int, limit: Int): List<User> {
        val result = remote.getListUser(offset, limit)

        if(!result.isSuccessful) {
            val errStr = result.errorBody()?.string()
            val err = errStr?.let {
                runCatching {
                    gson.fromJson(it, ErrorResponse::class.java)
                }.getOrNull()
            }

            throw ApiErrorException(
                errorCode = err?.errorCode ?: result.code().toString(),
                message = err?.errorMessage ?: "Request failed (${result.code()})",
                messageEn = err?.errorMessageEn
            )
        }

        return result.body()?.data?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getDetailUser(id: String): UserDetail {
        val result = remote.getDetailUser(id)

        if(!result.isSuccessful) {
            val errStr = result.errorBody()?.string()
            val err = errStr?.let {
                runCatching {
                    gson.fromJson(it, ErrorResponse::class.java)
                }.getOrNull()
            }

            throw ApiErrorException(
                errorCode = err?.errorCode ?: result.code().toString(),
                message = err?.errorMessage ?: "Request failed (${result.code()})",
                messageEn = err?.errorMessageEn
            )
        }

        return result.body()?.data?.toDomain()
            ?: throw ApiErrorException(null, "Data is null")
    }

    override suspend fun updateUser(
        id: String,
        body: UserRequest
    ): User {
        val result = remote.updateUser(id, body)

        if(!result.isSuccessful) {
            val errStr = result.errorBody()?.string()
            val err = errStr?.let {
                runCatching {
                    gson.fromJson(it, ErrorResponse::class.java)
                }.getOrNull()
            }

            throw ApiErrorException(
                errorCode = err?.errorCode ?: result.code().toString(),
                message = err?.errorMessage ?: "Request failed (${result.code()})",
                messageEn = err?.errorMessageEn
            )
        }

        return result.body()?.data?.toDomain()
            ?: throw ApiErrorException(null, "Data is null")
    }

}