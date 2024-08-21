package com.yaabelozerov.files.data.remote

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.core.util.Constants
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class XlsxApiImpl: XlsxApiService {
    override fun uploadExcelFile(
        eventId: Int,
        organizerId: Int,
        membersPath: String,
        file: MultipartBody.Part?
    ): Call<Unit> {
        Log.i("APICALL", "uploadExcelFile")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_EVENTS)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<Unit> = retrofit.create(XlsxApiService::class.java).uploadExcelFile(eventId, organizerId, membersPath, file)

        return call
    }
}