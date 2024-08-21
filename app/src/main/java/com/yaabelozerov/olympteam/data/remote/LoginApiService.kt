package com.yaabelozerov.olympteam.data.remote

import com.yaabelozerov.olympteam.data.remote.model.AccessDTO
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    @POST("sign_in")
    @FormUrlEncoded
    fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<AccessDTO>
}