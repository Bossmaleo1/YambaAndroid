package com.android.yambasama.presentation.di

import android.app.Application
import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.domain.usecase.address.GetAddressUseCase
import com.android.yambasama.domain.usecase.annoucement.CreateAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import com.android.yambasama.domain.usecase.user.*
import com.android.yambasama.presentation.viewModel.announcement.AnnouncementViewModelFactory
import com.android.yambasama.presentation.viewModel.address.AddressViewModelFactory
import com.android.yambasama.presentation.viewModel.drop.DropViewModelFactory
import com.android.yambasama.presentation.viewModel.searchForm.SearchFormViewModelFactory
import com.android.yambasama.presentation.viewModel.token.TokenDataStoreViewModelFactory
import com.android.yambasama.presentation.viewModel.user.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        getUserUseCase: GetUserUseCase,
        getTokenUseCase: GetTokenUseCase,
        saveUserUseCase: SaveUserUseCase,
        saveTokenUseCase: SaveTokenUseCase,
        getSavedUserUseCase: GetSavedUserUseCase,
        getSavedTokenUseCase: GetSavedTokenUseCase
    ): UserViewModelFactory {
        return UserViewModelFactory(
            getUserUseCase,
            getTokenUseCase,
            saveUserUseCase,
            saveTokenUseCase,
            getSavedUserUseCase,
            getSavedTokenUseCase
        )
    }

    @Singleton
    @Provides
    fun provideAddressViewModelFactory(
        getAddressUseCase: GetAddressUseCase
    ): AddressViewModelFactory {
        return  AddressViewModelFactory(
            getAddressUseCase
        )
    }

    @Singleton
    @Provides
    fun provideDropViewModelFactory(
        application: Application,
        deleteTableUserUseCase: DeleteTableUserUseCase,
        deleteTableTokenUseCase: DeleteTableTokenUseCase
    ): DropViewModelFactory {
        return DropViewModelFactory(
            deleteTableUserUseCase,
            deleteTableTokenUseCase
        )
    }

    @Singleton
    @Provides
    fun provideSearchFormViewModelFactory(): SearchFormViewModelFactory {
        return SearchFormViewModelFactory()
    }

    @Singleton
    @Provides
    fun provideAnnouncementViewModelFactory(
        getAnnouncementsUseCase: GetAnnouncementsUseCase,
        getAnnouncementUseCase: GetAnnouncementUseCase,
        createAnnouncementUseCase: CreateAnnouncementUseCase
    ): AnnouncementViewModelFactory {
        return AnnouncementViewModelFactory(
            getAnnouncementsUseCase,
            getAnnouncementUseCase,
            createAnnouncementUseCase
        )
    }

    @Singleton
    @Provides
    fun provideTokenDataStoreViewModelFactory(
         tokenManager: TokenManager
    ): TokenDataStoreViewModelFactory {
        return TokenDataStoreViewModelFactory(
            tokenManager = tokenManager
        )
    }
}