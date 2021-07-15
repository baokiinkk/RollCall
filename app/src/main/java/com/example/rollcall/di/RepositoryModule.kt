package com.example.rollcall.di

import com.example.rollcall.data.respository.admin.AdminRepository
import com.example.rollcall.data.respository.admin.AdminRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun repository(repository: AdminRepositoryImpl): AdminRepository
}