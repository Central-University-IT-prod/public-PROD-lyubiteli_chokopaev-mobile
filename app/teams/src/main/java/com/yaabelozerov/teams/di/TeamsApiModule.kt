package com.yaabelozerov.teams.di

import com.yaabelozerov.teams.data.remote.source.TeamsApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeamsApiModule {
    @Provides
    @Singleton
    fun provideUserApi(): TeamsApiImpl {
        return TeamsApiImpl()
    }
}