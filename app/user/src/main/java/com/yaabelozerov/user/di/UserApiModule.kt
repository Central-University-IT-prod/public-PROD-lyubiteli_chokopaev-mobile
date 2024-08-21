package com.yaabelozerov.user.di

import com.yaabelozerov.user.data.remote.source.UserApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserApiModule {
    @Provides
    @Singleton
    fun provideUserApi(): UserApiImpl {
        return UserApiImpl()
    }
}