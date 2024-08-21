package com.yaabelozerov.user.domain.repository

import com.yaabelozerov.core.util.Resource
import com.yaabelozerov.user.domain.model.UserData

interface UserRepository {
    suspend fun getUserById(uid: String): Resource<UserData>
    suspend fun getCurrentUserData(): Resource<UserData>
    suspend fun patchCurrentUser(data: UserData)
}