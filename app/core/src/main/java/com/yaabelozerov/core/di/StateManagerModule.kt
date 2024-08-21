package com.yaabelozerov.core.di

import com.yaabelozerov.core.util.GlobalStateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StateManagerModule {
    @Provides
    @Singleton
    fun provideStateManager(): GlobalStateManager {
        return GlobalStateManager()
    }
}