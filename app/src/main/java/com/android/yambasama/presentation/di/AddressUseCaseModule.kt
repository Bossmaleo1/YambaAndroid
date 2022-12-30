package com.android.yambasama.presentation.di

import com.android.yambasama.domain.repository.AddressRepository
import com.android.yambasama.domain.usecase.address.GetAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AddressUseCaseModule {

    @Singleton
    @Provides
    fun provideGetAddressUseCase(
        addressRepository: AddressRepository
    ):GetAddressUseCase {
        return GetAddressUseCase(addressRepository)
    }
}