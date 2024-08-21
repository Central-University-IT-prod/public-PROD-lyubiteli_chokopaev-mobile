package com.yaabelozerov.olympteam.di

import com.yaabelozerov.olympteam.data.remote.source.LoginApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LoginApiModule {
    @Provides
    @Singleton
    fun provideLoginApi(): LoginApiImpl {
        return LoginApiImpl()
    }
}