package com.yaabelozerov.olympteam.data.remote.source

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.core.util.Constants
import com.yaabelozerov.olympteam.data.remote.RegisterApiService
import com.yaabelozerov.olympteam.data.remote.model.AccessDTO
import com.yaabelozerov.olympteam.data.remote.model.RegisterDTO
import com.yaabelozerov.user.data.remote.UserApiService
import com.yaabelozerov.user.data.remote.model.UserDTO
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration

class RegisterApiImpl : RegisterApiService {
    override fun createUser(request: RegisterDTO): Call<AccessDTO> {
        Log.d("APICALL", "createUser")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_USER)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<AccessDTO> =
            retrofit.create(RegisterApiService::class.java).createUser(request = request)

        return call
    }
}