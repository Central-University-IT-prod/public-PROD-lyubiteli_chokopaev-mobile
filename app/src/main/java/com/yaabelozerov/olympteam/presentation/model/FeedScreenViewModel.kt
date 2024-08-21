package com.yaabelozerov.olympteam.presentation.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.core.util.Resource
import com.yaabelozerov.stats.domain.repository.StatsRepository
import com.yaabelozerov.teams.domain.repository.TeamsRepository
import com.yaabelozerov.user.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val teamsRepository: TeamsRepository,
    private val userRepository: UserRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {
    var feed = MutableStateFlow(FeedScreenState())

    fun loadTeams() {
        viewModelScope.launch {
            try {
                Log.d("userrep", userRepository.getCurrentUserData().data.toString())
                val uid = userRepository.getCurrentUserData().data!!.id
                when (val res = teamsRepository.getPossible(uid!!, 1)) {
                    is Resource.Success -> {
                        feed.update {
                            feed.value.copy(
                                teams = res.data!!,
                                users = feed.value.users, isLoading = false, error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        feed.update {
                            feed.value.copy(
                                teams = emptyList(),
                                users = feed.value.users,
                                isLoading = false,
                                error = res.message
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun applyForTeam(teamId: Int) {
        viewModelScope.launch {
            try {
                val userId = userRepository.getCurrentUserData().data!!.id!!
                teamsRepository.applyForTeam(userId, teamId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadStats() {
        viewModelScope.launch {
            feed.update { FeedScreenState(langStats = statsRepository.getLangs(), roleStats = statsRepository.getRoles()) }
        }
    }
}