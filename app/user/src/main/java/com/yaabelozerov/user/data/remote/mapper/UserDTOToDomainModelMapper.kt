package com.yaabelozerov.user.data.remote.mapper

import com.yaabelozerov.user.data.remote.model.UserDTO
import com.yaabelozerov.user.domain.model.UserData

class UserDTOToDomainModelMapper {
    fun toDomainModel(dto: UserDTO): UserData {
        return UserData(
            id = dto.id,
            name = dto.name,
            surname = dto.surname,
            patronymic = dto.patronymic ?: "",
            email = dto.email,
            isAdmin = dto.isAdmin,
            avatarUrl = dto.photo ?: "",
            tags = (dto.tags ?: emptyList()),
            langs = dto.langs ?: emptyList(),
            role = dto.role ?: "Не выбрана",
            tgId = dto.tgUsername,
        )
    }

    fun fromDomainModel(data: UserData): UserDTO {
        return UserDTO(
            id = data.id,
            name = data.name,
            surname = data.surname,
            patronymic = data.patronymic,
            email = data.email,
            photo = data.avatarUrl,
            tgUsername = data.tgId,
            isAdmin = data.isAdmin,
            role = data.role,
            tags = data.tags,
            langs = data.langs
        )
    }
}