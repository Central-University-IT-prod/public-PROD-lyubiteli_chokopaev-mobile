package com.yaabelozerov.teams.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yaabelozerov.teams.data.remote.model.TeamsDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface TeamsApiService {
    @POST("myTeam")
    fun getTeamById(
        @Body body: UserEventRequestBody
    ): Call<TeamsDTO>

    @DELETE("deleteMember")
    fun removeUserFromTeam(
        @Query("user_id") userId: Int,
        @Query("team_id") teamId: Int
    ): Call<UserTeamRequestBody>

    @PATCH("update")
    fun patchTeamById(
        @Body body: TeamPatch
    ): Call<Unit>

    @GET("possibleTeams")
    fun getPossibleTeams(
        @Query ("user_id") userId: Int,
        @Query ("event_id") eventId: Int
    ): Call<List<TeamsDTO>>

    @POST("createInvite")
    fun applyToTeam(
        @Body body: InviteTeam
    ): Call<Unit>

    @POST("create")
    fun create(
        @Body body: CreateTeam
    ): Call<Unit>

    @DELETE("remove")
    fun removeTeam(
        @Query("team_id") teamId: Int
    ): Call<Unit>
}

@JsonClass(generateAdapter = true)
data class UserEventRequestBody (
    @Json(name="user_id")
    val userId: Int?,
    @Json(name="event_id")
    val eventId: Int?
)

@JsonClass(generateAdapter = true)
data class UserTeamRequestBody (
    @Json(name = "used_id")
    val userId: Int?,
    @Json(name ="team_id")
    val teamId: Int?
)

@JsonClass(generateAdapter = true)
data class InviteTeam (
    @Json(name="team_id")
    val teamId: Int,
    @Json(name="user_id")
    val userId: Int,
    @Json(name="from_team")
    val fromTeam: Boolean
)

@JsonClass(generateAdapter = true)
data class CreateTeam (
    @Json(name="author_id") val leaderId: Int,
    @Json(name="event_id") val eventId: Int,
    @Json(name="name") val name: String,
    @Json(name="size") val size: Int,
    @Json(name="description") val description: String?,
    @Json(name="need") val need: List<String>
)

@JsonClass(generateAdapter = true)
data class GenericResponse(
    @Json(name="status") val status: String
)

@JsonClass(generateAdapter = true)
data class TeamPatch(
    @Json(name="team_id") val teamId: Int,
    @Json(name="name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name="size") val maxSize: Int?,
    @Json(name="need") val need: List<String>?
)