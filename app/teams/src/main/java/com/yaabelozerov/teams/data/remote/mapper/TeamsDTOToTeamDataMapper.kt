package com.yaabelozerov.teams.data.remote.mapper

import android.util.Log
import com.yaabelozerov.teams.data.remote.model.TeamsDTO
import com.yaabelozerov.teams.domain.model.TeamData
import com.yaabelozerov.user.data.remote.mapper.UserDTOToDomainModelMapper
import com.yaabelozerov.user.data.remote.source.UserApiImpl
import com.yaabelozerov.user.di.DataStoreManager
import kotlinx.coroutines.flow.first
import retrofit2.await

class TeamsDTOToTeamDataMapper(
    private val userApiImpl: UserApiImpl, private val dataStoreManager: DataStoreManager
) {

    suspend fun toDomainModel(dto: TeamsDTO): TeamData {
        val mapper = UserDTOToDomainModelMapper()
        Log.d("APICALL_MAPPER", "teamDTOtoDomainModel")
        return TeamData(id = dto.id ?: 0,
            name = dto.name ?: "",
            description = dto.description ?: "",
            tags = dto.tags ?: emptyList(),
            need = dto.need ?: emptyList(),
            maxSize = dto.size ?: 0,
            leader = mapper.toDomainModel(
                userApiImpl.getUserByUid(
                    dto.authorId!!, dataStoreManager.currentUserToken.first()
                ).await()
            ),
            members = dto.members?.map {
                mapper.toDomainModel(
                    userApiImpl.getUserByUid(
                        it, dataStoreManager.currentUserToken.first()
                    ).await()
                )
            } ?: emptyList()
        )
    }

    fun fromDomainModel(data: TeamData): TeamsDTO {
        return TeamsDTO(
            id = data.id,
            authorId = data.leader?.id,
            name = data.name,
            size = data.members?.size,
            description = data.description,
            need = data.need,
            tags = data.tags,
            members = data.members?.map { it.id!! }
        )
    }
}