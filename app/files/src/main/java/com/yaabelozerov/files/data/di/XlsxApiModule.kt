package com.yaabelozerov.files.data.di

import com.yaabelozerov.files.data.remote.XlsxApiImpl
import com.yaabelozerov.files.data.remote.XlsxApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object XlsxApiModule {
    @Provides
    @Singleton
    fun provideXlsxApi(): XlsxApiImpl {
        return XlsxApiImpl()
    }
}