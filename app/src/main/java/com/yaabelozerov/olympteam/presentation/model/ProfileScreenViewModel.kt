package com.yaabelozerov.olympteam.presentation.model

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaabelozerov.core.util.GlobalStateManager
import com.yaabelozerov.core.util.Resource
import com.yaabelozerov.files.data.domain.FileRepository
import com.yaabelozerov.teams.data.remote.TeamPatch
import com.yaabelozerov.teams.data.remote.mapper.TeamsDTOToTeamDataMapper
import com.yaabelozerov.teams.data.remote.model.TeamsDTO
import com.yaabelozerov.teams.domain.model.TeamData
import com.yaabelozerov.teams.domain.repository.TeamsRepository
import com.yaabelozerov.user.data.remote.source.UserApiImpl
import com.yaabelozerov.user.di.DataStoreManager
import com.yaabelozerov.user.domain.model.UserData
import com.yaabelozerov.user.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val teamsRepository: TeamsRepository,
    private val userApiImpl: UserApiImpl,
    private val dataStoreManager: DataStoreManager,
    private val fileRepository: FileRepository
) : ViewModel() {
    var user = MutableStateFlow(ProfileScreenState())

    fun loadCurrentUser(accessState: MutableStateFlow<String>) {
        viewModelScope.launch {
            try {
                when (val userRes = userRepository.getCurrentUserData()) {
                    is Resource.Error -> {
                        accessState.update { "LOGGEDOUT" }
                        user.emit(ProfileScreenState(isLoading = false, error = userRes.message))
                    }

                    is Resource.Success -> {
                        try {
                            when (val teamRes =
                                teamsRepository.getTeamByUserId(userRes.data!!.id ?: 1)) {
                                is Resource.Error -> {
                                    accessState.update { if (userRes.data!!.isAdmin!!) "ADMIN" else "MEMBER" }
                                    Log.i("teamRes", teamRes.message.toString())
                                    user.emit(
                                        ProfileScreenState(
                                            userData = userRes.data,
                                            teamData = null,
                                            isLoading = false,
                                            error = teamRes.message
                                        )
                                    )
                                }

                                is Resource.Success -> {
                                    if (userRes.data!!.isAdmin!!) {
                                        accessState.update { "ADMIN" }
                                    } else {
                                        if (userRes.data!!.id == teamRes.data!!.leader!!.id) {
                                            accessState.update { "LEADER" }
                                        } else {
                                            accessState.update { "MEMBER" }
                                        }
                                    }
                                    Log.i("teamDataViewModel", teamRes.data.toString())
                                    user.emit(
                                        ProfileScreenState(
                                            userData = userRes.data,
                                            teamData = teamRes.data,
                                            isLoading = false,
                                            error = null
                                        )
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            user.emit(ProfileScreenState(isLoading = false, error = e.message))
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                user.emit(ProfileScreenState(isLoading = false, error = e.message))
            }
        }
    }

    fun patchUserData(data: UserData) {
        viewModelScope.launch { userRepository.patchCurrentUser(data = data) }
    }

    fun removeUserFromTeam(uid: Int, tid: Int) {
        viewModelScope.launch { teamsRepository.removeUserFromTeam(uid, tid) }
    }

    fun patchTeamData(teamData: TeamData) {
        viewModelScope.launch {
            teamsRepository.patchTeam(
                TeamPatch(teamId = teamData.id!!, name = teamData.name, description = teamData.description, maxSize = teamData.maxSize, need = teamData.need)
            )
        }
    }

    fun createNewTeam(leaderId: Int, eventId: Int, name: String, size: Int, description: String, need: List<String>, accessState: MutableStateFlow<String>) {
        viewModelScope.launch {
            try {
                teamsRepository.create(
                    leaderId, eventId, name, size, description, need
                )
                accessState.update { "LEADER" }
            } catch (e: HttpException) {
                user.emit(ProfileScreenState(isLoading = false, error = "Некорректные данные. Команда может содержать от 3 до 5 человек"))
            } catch (e: Exception) {
                user.emit(ProfileScreenState(isLoading = false, error = "Неизвестная ошибка: ${e.message}"))
            }
        }
    }

    fun deleteTeam(teamId: Int) {
        viewModelScope.launch {
            try {
                teamsRepository.removeTeamById(teamId)
            } catch (e: Exception) {
                user.emit(ProfileScreenState(isLoading = false, error = e.message))
            }
        }
    }

    fun uploadUsers(uri: Uri) {
        viewModelScope.launch {
            try {
                fileRepository.uploadXlsx(uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}