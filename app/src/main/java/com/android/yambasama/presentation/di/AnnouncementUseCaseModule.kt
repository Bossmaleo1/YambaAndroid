package com.android.yambasama.presentation.di

import com.android.yambasama.domain.repository.AnnouncementRepository
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnnouncementUseCaseModule {

    @Singleton
    @Provides
    fun provideGetAnnouncementUseCase(
        announcementRepository: AnnouncementRepository
    ):GetAnnouncementsUseCase {
        return GetAnnouncementsUseCase(announcementRepository)
    }
}