package com.bullion.bulliontest.data.remote.api

import com.bullion.bulliontest.data.remote.request.LoginRequest
import com.bullion.bulliontest.data.remote.request.UserRequest
import com.bullion.bulliontest.data.remote.response.CommonResponse
import com.bullion.bulliontest.data.remote.response.UserResponse
import com.bullion.bulliontest.data.remote.response.LoginResponse
import com.bullion.bulliontest.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @POST("api/v1/auth/login")
    suspend fun login(
        @Body users: LoginRequest
    ): Response<CommonResponse<LoginResponse>>

    @Multipart
    @POST("api/v1/auth/register")
    suspend fun register(
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("date_of_birth") dateOfBirth: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("password") password: RequestBody,
    ): Response<CommonResponse<RegisterResponse?>>

    @GET("api/v1/users")
    suspend fun getListUser(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<CommonResponse<List<UserResponse>>>

    @POST("api/v1/admin/{id}/update")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body body: UserRequest
    ): Response<CommonResponse<UserResponse>>
}