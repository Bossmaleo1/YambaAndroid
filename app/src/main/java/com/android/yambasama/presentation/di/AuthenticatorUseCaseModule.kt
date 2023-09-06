package com.android.yambasama.presentation.di

import com.android.yambasama.domain.repository.AuthenticatorRepository
import com.android.yambasama.domain.usecase.user.UpdateSavedTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticatorUseCaseModule {
    @Singleton
    @Provides
    fun provideUpdateSavedTokenUseCase(
        authenticatorRepository: AuthenticatorRepository
    ): UpdateSavedTokenUseCase {
        return UpdateSavedTokenUseCase(authenticatorRepository)
    }
}