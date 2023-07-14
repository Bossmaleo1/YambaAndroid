package com.android.yambasama.presentation.di

import com.android.yambasama.domain.repository.AnnouncementRepository
import com.android.yambasama.domain.usecase.annoucement.CreateAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnnouncementsUseCaseModule {

    @Singleton
    @Provides
    fun provideGetAnnouncementsUseCase(
        announcementRepository: AnnouncementRepository
    ):GetAnnouncementsUseCase {
        return GetAnnouncementsUseCase(announcementRepository)
    }

    @Singleton
    @Provides
    fun provideGetAnnouncementUseCase(
        announcementRepository: AnnouncementRepository
    ):GetAnnouncementUseCase {
        return GetAnnouncementUseCase(announcementRepository)
    }

    @Singleton
    @Provides
    fun provideCreateAnnouncementUseCase(
        announcementRepository: AnnouncementRepository
    ): CreateAnnouncementUseCase {
        return CreateAnnouncementUseCase(announcementRepository)
    }
}