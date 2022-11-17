package com.android.yambasama.presentation.di

import com.android.yambasama.data.repository.UserRepositoryImpl
import com.android.yambasama.data.repository.dataSource.user.UserLocalDataSource
import com.android.yambasama.data.repository.dataSource.user.UserRemoteDataSource
import com.android.yambasama.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
    }
}