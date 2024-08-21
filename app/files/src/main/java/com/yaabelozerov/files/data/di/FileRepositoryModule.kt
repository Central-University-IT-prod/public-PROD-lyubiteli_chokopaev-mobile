package com.yaabelozerov.files.data.di

import com.yaabelozerov.files.data.domain.FileRepository
import com.yaabelozerov.files.data.repository.FileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FileRepositoryModule {
    @Binds
    @Singleton
    abstract
    fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository
}