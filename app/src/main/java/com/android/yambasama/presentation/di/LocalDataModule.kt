package com.android.yambasama.presentation.di

import com.android.yambasama.data.db.dao.UserDAO
import com.android.yambasama.data.repository.dataSource.user.UserLocalDataSource
import com.android.yambasama.data.repository.dataSourceImpl.user.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(userDAO: UserDAO): UserLocalDataSource {
        return UserLocalDataSourceImpl(userDAO)
    }
}