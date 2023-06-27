package com.android.yambasama.ui.UIEvent.Event

import com.android.yambasama.data.model.api.AnnouncementBody

sealed class AnnouncementFormEvent {

    data class AnnouncementSubmit(
        val announcementBody: AnnouncementBody
    ): AnnouncementFormEvent()

}