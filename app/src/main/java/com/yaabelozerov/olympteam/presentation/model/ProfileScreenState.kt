package com.yaabelozerov.olympteam.presentation.model

import android.net.Uri
import com.yaabelozerov.teams.domain.model.TeamData
import com.yaabelozerov.user.domain.model.UserData

data class ProfileScreenState(
    var userData: UserData? = UserData(
        1, "", "", "", "", false, "", emptyList(), emptyList(), "", ""
    ),
    var teamData: TeamData? = TeamData(),
    var isLoading: Boolean = true,
    var error: String? = null
)