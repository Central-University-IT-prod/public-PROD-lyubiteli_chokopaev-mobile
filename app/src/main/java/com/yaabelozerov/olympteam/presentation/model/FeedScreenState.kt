package com.yaabelozerov.olympteam.presentation.model

import com.yaabelozerov.teams.domain.model.TeamData
import com.yaabelozerov.user.domain.model.UserData

data class FeedScreenState(
    val teams: List<TeamData> = emptyList(),
    val users: List<UserData> = emptyList(),
    val langStats: Map<String, Double> = mapOf(),
    val roleStats: Map<String, Double> = mapOf(),
    val isLoading: Boolean = true,
    val error: String? = null,
)