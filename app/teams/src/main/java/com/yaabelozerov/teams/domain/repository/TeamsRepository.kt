package com.yaabelozerov.teams.domain.repository

import com.yaabelozerov.core.util.Resource
import com.yaabelozerov.teams.data.remote.GenericResponse
import com.yaabelozerov.teams.data.remote.TeamPatch
import com.yaabelozerov.teams.data.remote.model.TeamsDTO
import com.yaabelozerov.teams.domain.model.TeamData

interface TeamsRepository {
    suspend fun getTeamByUserId(userId: Int): Resource<TeamData>
    suspend fun removeUserFromTeam(userId: Int, teamId: Int)
    suspend fun patchTeam(data: TeamPatch)
    suspend fun getPossible(userId: Int, eventId: Int): Resource<List<TeamData>>
    suspend fun applyForTeam(userId: Int, teamId: Int)
    suspend fun create(leaderId: Int, eventId: Int, name: String, size: Int, description: String, need: List<String>)
    suspend fun removeTeamById(teamId: Int)
}