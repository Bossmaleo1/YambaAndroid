package com.android.yambasama.presentation.di

import com.android.yambasama.domain.repository.UserRepository
import com.android.yambasama.domain.usecase.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserUseCaseModule {
    @Singleton
    @Provides
    fun provideTokenUseCase(
        userRepository: UserRepository
    ): GetTokenUseCase {
        return GetTokenUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideSaveUserUseCase(
        userRepository: UserRepository
    ): SaveUserUseCase {
        return SaveUserUseCase(userRepository)
    }


    @Singleton
    @Provides
    fun provideGetSavedUserUseCase(
        userRepository: UserRepository
    ): GetSavedUserUseCase {
        return GetSavedUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteTablePublicMessageUseCase(
        userRepository: UserRepository
    ): DeleteTableUserUseCase {
        return DeleteTableUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedUserUseCase(
        userRepository: UserRepository
    ): DeleteSavedUserUseCase {
        return DeleteSavedUserUseCase(userRepository)
    }

}