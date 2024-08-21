package com.yaabelozerov.teams.domain.model

import com.yaabelozerov.user.domain.model.UserData

data class TeamData(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val tags: List<String>? = null,
    val need: List<String>? = null,
    val maxSize: Int? = null,
    val leader: UserData? = null,
    val members: List<UserData>? = null,
)