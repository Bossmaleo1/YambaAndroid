package com.android.yambasama.presentation.di

import com.android.yambasama.data.api.service.AddressAPIService
import com.android.yambasama.data.api.service.AnnouncementAPIService
import com.android.yambasama.data.api.service.UserAPIService
import com.android.yambasama.data.repository.dataSource.address.AddressRemoteDataSource
import com.android.yambasama.data.repository.dataSource.annoucement.AnnouncementRemoteDataSource
import com.android.yambasama.data.repository.dataSource.user.UserRemoteDataSource
import com.android.yambasama.data.repository.dataSourceImpl.address.AddressRemoteDataSourceImpl
import com.android.yambasama.data.repository.dataSourceImpl.annoucement.AnnouncementRemoteDataSourceImpl
import com.android.yambasama.data.repository.dataSourceImpl.user.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        userAPIService: UserAPIService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userAPIService)
    }

    @Singleton
    @Provides
    fun provideAddressRemoteDataSource(
        addressAPIService: AddressAPIService
    ): AddressRemoteDataSource {
        return AddressRemoteDataSourceImpl(addressAPIService)
    }

    @Singleton
    @Provides
    fun provideAnnouncemetRemoteDataSource(
        announcementAPIService: AnnouncementAPIService
    ): AnnouncementRemoteDataSource {
        return AnnouncementRemoteDataSourceImpl(announcementAPIService)
    }
}