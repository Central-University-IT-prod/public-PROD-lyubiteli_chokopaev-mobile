package com.yaabelozerov.teams.data.repository

import android.util.Log
import com.yaabelozerov.core.util.Resource
import com.yaabelozerov.teams.data.remote.CreateTeam
import com.yaabelozerov.teams.data.remote.InviteTeam
import com.yaabelozerov.teams.data.remote.TeamPatch
import com.yaabelozerov.teams.data.remote.UserEventRequestBody
import com.yaabelozerov.teams.data.remote.mapper.TeamsDTOToTeamDataMapper
import com.yaabelozerov.teams.data.remote.model.TeamsDTO
import com.yaabelozerov.teams.data.remote.source.TeamsApiImpl
import com.yaabelozerov.teams.domain.model.TeamData
import com.yaabelozerov.teams.domain.repository.TeamsRepository
import com.yaabelozerov.user.data.remote.source.UserApiImpl
import com.yaabelozerov.user.di.DataStoreManager
import retrofit2.HttpException
import retrofit2.await
import java.net.UnknownHostException
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor(
    private val teamsApiImpl: TeamsApiImpl,
    private val userApiImpl: UserApiImpl,
    private val dataStoreManager: DataStoreManager
) : TeamsRepository {
    override suspend fun getTeamByUserId(userId: Int): Resource<TeamData> {
        return try {
            val dto = teamsApiImpl.getTeamById(UserEventRequestBody(userId, 1)).await()
            Log.i("teamdto", dto.members.toString())
            val mapper = TeamsDTOToTeamDataMapper(userApiImpl, dataStoreManager)
            Resource.Success(
                data = mapper.toDomainModel(dto)
            )
        }
        catch (e:HttpException) {
            when (e.code()) {
                404 -> Resource.Error("User not in team")
                else -> Resource.Error(e.message ?: "Unknown error")
            }
        } catch (e: UnknownHostException) {
            Resource.Error("Network error")
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun removeUserFromTeam(userId: Int, teamId: Int) {
        try {
            teamsApiImpl.removeUserFromTeam(userId, teamId).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun patchTeam(data: TeamPatch) {
        try {
            teamsApiImpl.patchTeamById(data).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getPossible(userId: Int, eventId: Int): Resource<List<TeamData>> {
        try {
            val dtos = teamsApiImpl.getPossibleTeams(userId, eventId).await()
            Log.i("dtos", dtos.map { it.authorId }.joinToString(separator = " "))
            val mapper = TeamsDTOToTeamDataMapper(userApiImpl, dataStoreManager)
            return Resource.Success(dtos.map { mapper.toDomainModel(it) })
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun applyForTeam(userId: Int, teamId: Int) {
        try {
            teamsApiImpl.applyToTeam(InviteTeam(userId = userId, teamId = teamId, fromTeam = false)).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun create(
        leaderId: Int,
        eventId: Int,
        name: String,
        size: Int,
        description: String,
        need: List<String>,
    ) {
        try {
            teamsApiImpl.create(CreateTeam(leaderId, eventId, name, size, description, need)).await()
        } catch (e:Exception) {
            Log.i("EXCEPTION", "teams")
            e.printStackTrace()
        }
    }

    override suspend fun removeTeamById(teamId: Int) {
        try {
            Log.i("removeteamid", teamId.toString())
            teamsApiImpl.removeTeam(teamId = teamId).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}