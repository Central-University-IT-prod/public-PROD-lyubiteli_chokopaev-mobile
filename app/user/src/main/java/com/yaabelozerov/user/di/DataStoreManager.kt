package com.yaabelozerov.user.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    suspend fun setCurrentUserToken(mode: String) {
        settingsDataStore.edit { settings ->
            settings[stringPreferencesKey("token")] = mode
        }
    }

    val currentUserToken: Flow<String> = settingsDataStore.data.map { preferences ->
        preferences[stringPreferencesKey("token")] ?: ""
    }
}