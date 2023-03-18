package com.android.yambasama.ui.UIEvent.Event

import com.android.yambasama.data.model.dataRemote.Announcement

sealed class AnnouncementEvent {
    data class AnnouncementInt(
        val token: String,
        val departureAddressId: Int,
        val destinationAddressId: Int,
        val departureTime: String
    ): AnnouncementEvent()
    object IsNetworkConnected: AnnouncementEvent()
    object InitAnnouncementState:AnnouncementEvent()
    object IsNetworkError:AnnouncementEvent()
    data class ItemClicked(
        val announcement: Announcement
    ) : AnnouncementEvent()
}