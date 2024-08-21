package com.yaabelozerov.stats.di

import com.yaabelozerov.stats.data.remote.source.StatsApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatsApiModule {
    @Provides
    @Singleton
    fun provideStatsApi(): StatsApiImpl {
        return StatsApiImpl()
    }
}