package com.bullion.bulliontest.domain.mapper

import com.bullion.bulliontest.data.remote.response.LoginResponse
import com.bullion.bulliontest.data.remote.response.RegisterResponse
import com.bullion.bulliontest.data.remote.response.UserDetailResponse
import com.bullion.bulliontest.data.remote.response.UserResponse
import com.bullion.bulliontest.domain.model.Login
import com.bullion.bulliontest.domain.model.Register
import com.bullion.bulliontest.domain.model.User
import com.bullion.bulliontest.domain.model.UserDetail

fun LoginResponse.toDomain() = Login(name = name, email = email, token = token)
fun RegisterResponse.toDomain() = Register(name = name, email = email)
fun UserResponse.toDomain() = User(id = id, name = name, gender = gender, dateOfBirth = dateOfBirth, email = email, photo = photo, phone = phone, address = address)
fun UserDetailResponse.toDomain() = UserDetail(id = id, firstName = firstName, lastName = lastName, gender = gender, dateOfBirth = dateOfBirth, email = email, photo = photo, phone = phone, address = address)