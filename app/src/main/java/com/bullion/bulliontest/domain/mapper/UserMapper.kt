package com.bullion.bulliontest.domain.mapper

import com.bullion.bulliontest.core.util.DateUtil.formatDateDayMonthYear
import com.bullion.bulliontest.data.remote.response.LoginResponse
import com.bullion.bulliontest.data.remote.response.RegisterResponse
import com.bullion.bulliontest.data.remote.response.UserDetailResponse
import com.bullion.bulliontest.data.remote.response.UserResponse
import com.bullion.bulliontest.domain.model.Login
import com.bullion.bulliontest.domain.model.Register
import com.bullion.bulliontest.domain.model.User
import com.bullion.bulliontest.domain.model.UserDetail

fun LoginResponse.toDomainLogin() = Login(name = name, email = email, token = token)
fun RegisterResponse.toDomainRegister() = Register(name = name, email = email)
fun UserResponse.toDomainUser() = User(id = id, name = name, gender = gender, dateOfBirth = dateOfBirth, email = email, photo = photo, phone = phone, address = address)
fun UserDetailResponse.toDomainDetail() = UserDetail(
    id = id ?: "",
    firstName = firstName ?: "",
    lastName = lastName ?: "",
    gender = gender ?: "",
    dateOfBirth = dateOfBirth?.let { formatDateDayMonthYear(it) } ?: "",
    email = email ?: "",
    photo = photo ?: "",
    phone = phone ?: "",
    address = address ?: ""
)
