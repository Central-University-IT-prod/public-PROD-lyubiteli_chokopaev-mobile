package com.yaabelozerov.user.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.yaabelozerov.core.util.Resource
import com.yaabelozerov.user.data.remote.mapper.UserDTOToDomainModelMapper
import com.yaabelozerov.user.data.remote.source.UserApiImpl
import com.yaabelozerov.user.di.DataStoreManager
import com.yaabelozerov.user.domain.model.UserData
import com.yaabelozerov.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import retrofit2.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiImpl: UserApiImpl,
    private val dataStoreManager: DataStoreManager
) : UserRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override suspend fun getUserById(uid: String): Resource<UserData> {

        return try {
            val dto = apiImpl.getUserWithToken(dataStoreManager.currentUserToken.first()).await()
            val mapper = UserDTOToDomainModelMapper()
            Resource.Success(data = mapper.toDomainModel(dto))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCurrentUserData(): Resource<UserData> {
        try {
            val dto = apiImpl.getUserWithToken(dataStoreManager.currentUserToken.first()).await()
            Log.i("dtoFromRepo", dto.toString())
            val mapper = UserDTOToDomainModelMapper()
            return Resource.Success(data = mapper.toDomainModel(dto))
        } catch (e: Exception) {
            return Resource.Error(message = e.message ?: "Unknown error")
        }
    }

    override suspend fun patchCurrentUser(data: UserData) {
        try {
            val mapper = UserDTOToDomainModelMapper()
            Log.i("mappedDataUpdate", mapper.fromDomainModel(data).toString())
            apiImpl.patchUserWithToken(
                authToken = dataStoreManager.currentUserToken.first(),
                body = mapper.fromDomainModel(data)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}