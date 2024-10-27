package com.kareemdev.fakestore.di

import android.content.Context
import com.kareemdev.fakestore.data.remote.retrofit.ApiService
import com.kareemdev.fakestore.repositories.UserRepository
import com.kareemdev.fakestore.repositories.datastore.DataStoreOperations
import com.kareemdev.fakestore.repositories.datastore.DataStoreOperationsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreOperations {
        return DataStoreOperationsImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

    /*@Provides
    @Singleton
    fun provideNoteRepository(apiService: ApiService): NoteRepository {
        return NoteRepository(apiService)
    }*/
}