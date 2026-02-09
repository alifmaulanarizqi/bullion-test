package com.bullion.bulliontest.data.repository

import com.bullion.bulliontest.data.remote.request.LoginRequest
import com.bullion.bulliontest.data.remote.request.UserRequest
import com.bullion.bulliontest.domain.model.Login
import com.bullion.bulliontest.domain.model.Register
import com.bullion.bulliontest.domain.model.User
import okhttp3.MultipartBody

interface UserRepository {
    suspend fun login(users: LoginRequest): Login
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
    ): Register
    suspend fun getListUser(offset: Int, limit: Int): List<User>
    suspend fun getDetailUser(id: String): User
    suspend fun updateUser(id: String, body: UserRequest): User
}