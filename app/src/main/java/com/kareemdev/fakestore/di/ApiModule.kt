package com.kareemdev.fakestore.di

import com.kareemdev.fakestore.data.remote.retrofit.ApiConfig
import com.kareemdev.fakestore.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiConfig.apiService
}