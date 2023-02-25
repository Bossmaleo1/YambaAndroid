package com.android.yambasama.presentation.viewModel.Announcement

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.yambasama.domain.usecase.annoucement.GetAnnouncementsUseCase
import javax.inject.Inject

class AnnouncementViewModel   @Inject constructor(
    private val app: Application,
    private val getAnnoucementsUseCase: GetAnnouncementsUseCase
) : AndroidViewModel(app) {

}