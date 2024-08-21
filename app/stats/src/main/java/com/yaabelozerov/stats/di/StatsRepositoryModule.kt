package com.yaabelozerov.stats.di

import com.yaabelozerov.stats.data.remote.repository.StatsRepositoryImpl
import com.yaabelozerov.stats.domain.repository.StatsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StatsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindStatsRepository(statsRepositoryImpl: StatsRepositoryImpl): StatsRepository
}