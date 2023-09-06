package com.android.yambasama.presentation.di

import com.android.yambasama.data.repository.AddressRepositoryImpl
import com.android.yambasama.data.repository.AnnouncementRepositoryImpl
import com.android.yambasama.data.repository.AuthenticatorRepositoryImpl
import com.android.yambasama.data.repository.UserRepositoryImpl
import com.android.yambasama.data.repository.dataSource.address.AddressRemoteDataSource
import com.android.yambasama.data.repository.dataSource.annoucement.AnnouncementRemoteDataSource
import com.android.yambasama.data.repository.dataSource.authenticator.AuthenticatorLocalDataSource
import com.android.yambasama.data.repository.dataSource.user.UserLocalDataSource
import com.android.yambasama.data.repository.dataSource.user.UserRemoteDataSource
import com.android.yambasama.domain.repository.AddressRepository
import com.android.yambasama.domain.repository.AnnouncementRepository
import com.android.yambasama.domain.repository.AuthenticatorRepository
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

    @Singleton
    @Provides
    fun provideAddressRepository(
        addressRemoteDataSource: AddressRemoteDataSource
    ): AddressRepository {
        return AddressRepositoryImpl(addressRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideAnnouncementRepository(
        announcementRemoteDataSource: AnnouncementRemoteDataSource
    ): AnnouncementRepository {
        return AnnouncementRepositoryImpl(announcementRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideAuthenticatorRepository(
        authenticatorLocalDataSource: AuthenticatorLocalDataSource
    ): AuthenticatorRepository {
        return AuthenticatorRepositoryImpl(
            authenticatorLocalDataSource
        )
    }
}