package com.yaabelozerov.olympteam.di

import com.yaabelozerov.olympteam.data.remote.source.RegisterApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterApiService {
    @Provides
    @Singleton
    fun bindRegisterApiImpl(): RegisterApiImpl {
        return RegisterApiImpl()
    }
}