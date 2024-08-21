package com.yaabelozerov.stats.data.remote

import com.yaabelozerov.stats.data.remote.model.StatsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StatsApiService {
    @GET("statistics/{id}")
    fun getStatsForEvent(
        @Path("id") eventId: Int
    ): Call<StatsDTO>
}