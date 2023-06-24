package com.android.yambasama.presentation.viewModel.announcement

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementUseCase
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase

class AnnouncementViewModelFactory (
        private val app: Application,
        private val getAnnoucementsUseCase: GetAnnouncementsUseCase,
        private val getAnnouncementUseCase: GetAnnouncementUseCase
    ) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnnouncementViewModel(
            app,
            getAnnoucementsUseCase,
            getAnnouncementUseCase
        ) as T
    }

}