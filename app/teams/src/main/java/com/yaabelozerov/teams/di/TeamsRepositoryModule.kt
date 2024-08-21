package com.yaabelozerov.teams.di

import com.yaabelozerov.teams.data.repository.TeamsRepositoryImpl
import com.yaabelozerov.teams.domain.repository.TeamsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TeamsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTeamsRepository(teamsRepositoryImpl: TeamsRepositoryImpl): TeamsRepository
}