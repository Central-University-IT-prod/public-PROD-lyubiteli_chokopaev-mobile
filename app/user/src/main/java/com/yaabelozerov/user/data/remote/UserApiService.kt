package com.yaabelozerov.user.data.remote

import com.yaabelozerov.user.data.remote.model.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("get_user/{user_id}")
    fun getUserByUid(
        @Path("user_id") uid: Int,
        @Header("Authorization") authToken: String
    ): Call<UserDTO>

    @GET("get_me")
    fun getUserWithToken(
        @Header("Authorization") authToken: String
    ): Call<UserDTO>

    @PATCH("patch_me")
    fun patchUserWithToken(
        @Header("Authorization") authToken: String,
        @Body body: UserDTO
    ): Call<UserDTO>
}