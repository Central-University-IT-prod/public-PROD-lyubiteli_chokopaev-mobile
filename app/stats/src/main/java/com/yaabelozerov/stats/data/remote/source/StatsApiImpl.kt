package com.yaabelozerov.stats.data.remote.source

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.core.util.Constants
import com.yaabelozerov.stats.data.remote.StatsApiService
import com.yaabelozerov.stats.data.remote.model.StatsDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class StatsApiImpl: StatsApiService {
    override fun getStatsForEvent(eventId: Int): Call<StatsDTO> {
        Log.i("APICALL", "getStatsForEvent")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<StatsDTO> = retrofit.create(StatsApiService::class.java).getStatsForEvent(eventId)

        return call
    }
}