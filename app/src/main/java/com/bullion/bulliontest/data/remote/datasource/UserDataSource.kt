package com.bullion.bulliontest.data.remote.datasource

import com.bullion.bulliontest.data.remote.api.UserApi
import com.bullion.bulliontest.data.remote.request.LoginRequest
import com.bullion.bulliontest.data.remote.request.UserRequest
import com.bullion.bulliontest.data.remote.request.toTextRequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val api: UserApi
) {
    suspend fun login(body: LoginRequest) = api.login(body)
    suspend fun register(
        firstName: String,
        lastName: String,
        gender: String,
        dateOfBirth: String,
        email: String,
        phone: String,
        address: String,
        photo: MultipartBody.Part,
        password: String,
    ) {
        api.register(
            firstName = firstName.toTextRequestBody(),
            lastName = lastName.toTextRequestBody(),
            gender = gender.toTextRequestBody(),
            dateOfBirth = dateOfBirth.toTextRequestBody(),
            email = email.toTextRequestBody(),
            phone = phone.toTextRequestBody(),
            address = address.toTextRequestBody(),
            photo = photo,
            password = password.toTextRequestBody()
        )
    }

    suspend fun getListUser(offset: Int, limit: Int) = api.getListUser(offset, limit)
    suspend fun updateUser(id: String, body: UserRequest) = api.updateUser(id, body)
}