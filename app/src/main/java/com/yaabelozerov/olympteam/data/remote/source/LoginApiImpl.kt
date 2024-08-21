package com.yaabelozerov.olympteam.data.remote.source

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.olympteam.data.remote.LoginApiService
import com.yaabelozerov.olympteam.data.remote.model.AccessDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class LoginApiImpl: LoginApiService {
    override fun signIn(username: String, password: String): Call<AccessDTO> {
        Log.d("APICALL", "signIn")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(com.yaabelozerov.core.util.Constants.BASE_URL_USER).addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<AccessDTO> = retrofit.create(LoginApiService::class.java).signIn(username, password)

        return call
    }
}