package com.android.yambasama.ui.UIEvent.Event

import com.android.yambasama.data.model.dataRemote.Announcement

sealed class AnnouncementEvent {
    data class AnnouncementInt(
        val token: String,
        val departureAddressId: Int,
        val destinationAddressId: Int,
        val arrivingTimeAfter: String,
        val arrivingTimeBefore: String,
        val refreshing: Boolean
    ): AnnouncementEvent()
    object IsNetworkConnected: AnnouncementEvent()
    object InitAnnouncementState:AnnouncementEvent()
    object IsNetworkError:AnnouncementEvent()

    object IsEmptyAnnouncement:AnnouncementEvent()
    data class ItemClicked(
        val announcement: Announcement
    ) : AnnouncementEvent()
}