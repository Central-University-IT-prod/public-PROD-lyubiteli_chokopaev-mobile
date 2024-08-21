package com.yaabelozerov.teams.data.remote.source

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.core.util.Constants
import com.yaabelozerov.teams.data.remote.CreateTeam
import com.yaabelozerov.teams.data.remote.GenericResponse
import com.yaabelozerov.teams.data.remote.InviteTeam
import com.yaabelozerov.teams.data.remote.TeamPatch
import com.yaabelozerov.teams.data.remote.UserEventRequestBody
import com.yaabelozerov.teams.data.remote.TeamsApiService
import com.yaabelozerov.teams.data.remote.UserTeamRequestBody
import com.yaabelozerov.teams.data.remote.model.TeamsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TeamsApiImpl : TeamsApiService {
    override fun getTeamById(body: UserEventRequestBody): Call<TeamsDTO> {
        Log.d("APICALL", "getTeamById")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<TeamsDTO> = retrofit.create(TeamsApiService::class.java).getTeamById(
            body
        )

        return call
    }

    override fun removeUserFromTeam(userId: Int, teamId: Int): Call<UserTeamRequestBody> {
        Log.d("APICALL", "removeUserFromTeam")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<UserTeamRequestBody> = retrofit.create(TeamsApiService::class.java).removeUserFromTeam(
            userId, teamId
        )

        return call
    }

    override fun patchTeamById(body: TeamPatch): Call<Unit> {
        Log.i("APICALL", "patchByTeamId")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<Unit> = retrofit.create(TeamsApiService::class.java).patchTeamById(body)

//        call.clone().enqueue(
//            object : Callback<Any> {
//                override fun onResponse(p0: Call<Any>, p1: Response<Any>) {}
//
//                override fun onFailure(p0: Call<Any>, p1: Throwable) {
////                    Log.e("UserApiImpl", "API call failure")
//                    p1.printStackTrace()
//                }
//            }
//        )

        return call
    }

    override fun getPossibleTeams(userId: Int, eventId: Int): Call<List<TeamsDTO>> {
        Log.i("APICALL", "getPossibleTeams")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<List<TeamsDTO>> = retrofit.create(TeamsApiService::class.java).getPossibleTeams(userId, eventId)

        return call
    }

    override fun applyToTeam(body: InviteTeam): Call<Unit> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<Unit> = retrofit.create(TeamsApiService::class.java).applyToTeam(body)

        call.clone().enqueue(
            object : Callback<Unit> {
                override fun onResponse(p0: Call<Unit>, p1: Response<Unit>) {}

                override fun onFailure(p0: Call<Unit>, p1: Throwable) {
                    p1.printStackTrace()
                }
            }
        )

        return call
    }

    override fun create(body: CreateTeam): Call<Unit> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<Unit> = retrofit.create(TeamsApiService::class.java).create(body)

        return call
    }

    override fun removeTeam(teamId: Int): Call<Unit> {
        Log.i("APICALL", "removeTeam")
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL_TEAM)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        val call: Call<Unit> = retrofit.create(TeamsApiService::class.java).removeTeam(teamId)

        return call
    }
}