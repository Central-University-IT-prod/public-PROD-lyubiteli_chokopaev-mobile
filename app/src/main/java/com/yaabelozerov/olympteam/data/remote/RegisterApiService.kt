package com.yaabelozerov.olympteam.data.remote

import com.yaabelozerov.olympteam.data.remote.model.AccessDTO
import com.yaabelozerov.olympteam.data.remote.model.RegisterDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("create_user")
    fun createUser(
        @Body request: RegisterDTO
    ): Call<AccessDTO>
}