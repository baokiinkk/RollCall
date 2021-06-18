package com.example.rollcall.di

import com.example.rollcall.data.respository.Repository
import com.example.rollcall.data.respository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: RepositoryImpl):Repository
}