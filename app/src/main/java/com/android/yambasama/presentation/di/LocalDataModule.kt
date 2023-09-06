package com.android.yambasama.presentation.di

import com.android.yambasama.data.db.dao.AuthenticatorDAO
import com.android.yambasama.data.db.dao.UserDAO
import com.android.yambasama.data.repository.dataSource.authenticator.AuthenticatorLocalDataSource
import com.android.yambasama.data.repository.dataSource.user.UserLocalDataSource
import com.android.yambasama.data.repository.dataSourceImpl.authenticator.AuthenticatorLocalDataSourceImpl
import com.android.yambasama.data.repository.dataSourceImpl.user.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideUserLocalDataSource(userDAO: UserDAO): UserLocalDataSource {
        return UserLocalDataSourceImpl(userDAO)
    }

    @Singleton
    @Provides
    fun provideAuthenticatorLocalDataSource(authenticatorDAO: AuthenticatorDAO): AuthenticatorLocalDataSource {
        return AuthenticatorLocalDataSourceImpl(authenticatorDAO)
    }

}