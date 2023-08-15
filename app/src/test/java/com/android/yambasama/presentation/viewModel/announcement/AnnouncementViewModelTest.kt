package com.android.yambasama.presentation.viewModel.announcement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.yambasama.domain.usecase.annoucement.CreateAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

class AnnouncementViewModelTest {

    //subject under test
    private lateinit var announcementViewModel: AnnouncementViewModel
    private lateinit var getAnnoucementsUseCase: GetAnnouncementsUseCase
    private lateinit var getAnnouncementUseCase: GetAnnouncementUseCase
    private lateinit var createAnnouncementUseCase: CreateAnnouncementUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        getAnnoucementsUseCase = Mockito.mock(GetAnnouncementsUseCase::class.java)
        getAnnouncementUseCase = Mockito.mock(GetAnnouncementUseCase::class.java)
        createAnnouncementUseCase = Mockito.mock(CreateAnnouncementUseCase::class.java)
        announcementViewModel = AnnouncementViewModel(getAnnoucementsUseCase,getAnnouncementUseCase,createAnnouncementUseCase)
    }
}