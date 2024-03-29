package com.android.yambasama.presentation.viewModel.announcement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.domain.usecase.annoucement.CreateAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase

class AnnouncementViewModelFactory (
        private val getAnnoucementsUseCase: GetAnnouncementsUseCase,
        private val getAnnouncementUseCase: GetAnnouncementUseCase,
        private val createAnnouncementUseCase: CreateAnnouncementUseCase
    ) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnnouncementViewModel(
            getAnnoucementsUseCase,
            getAnnouncementUseCase,
            createAnnouncementUseCase
        ) as T
    }

}