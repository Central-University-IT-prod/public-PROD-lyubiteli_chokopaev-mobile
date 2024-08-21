package com.yaabelozerov.user.data.remote.source

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.core.util.Constants
import com.yaabelozerov.user.data.remote.UserApiService
import com.yaabelozerov.user.data.remote.model.UserDTO
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration

class UserApiImpl : UserApiService {
    override fun getUserByUid(uid: Int, authToken: String): Call<UserDTO> {
        Log.d("APICALL", "getUserByUid")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_USER)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<UserDTO> = retrofit.create(UserApiService::class.java).getUserByUid(
            uid,
            "Bearer $authToken"
        )

        return call
    }

    override fun getUserWithToken(authToken: String): Call<UserDTO> {
        Log.d("APICALL", "getUserWithToken")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_USER)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<UserDTO> = retrofit.create(UserApiService::class.java).getUserWithToken(
            "Bearer $authToken"
        )

        return call
    }

    override fun patchUserWithToken(authToken: String, body: UserDTO): Call<UserDTO> {
        Log.i("userapi", body.toString())

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_USER)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<UserDTO> = retrofit.create(UserApiService::class.java).patchUserWithToken(
            authToken = "Bearer $authToken", body = body
        )

        call.clone().enqueue(
            object : Callback<UserDTO> {
                override fun onResponse(p0: Call<UserDTO>, p1: Response<UserDTO>) {}

                override fun onFailure(p0: Call<UserDTO>, p1: Throwable) {
                    Log.e("UserApiImpl", "API call failure")
                    p1.printStackTrace()
                }
            }
        )

        return call
    }
}