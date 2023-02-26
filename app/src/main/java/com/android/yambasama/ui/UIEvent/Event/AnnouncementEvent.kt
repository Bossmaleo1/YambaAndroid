package com.android.yambasama.ui.UIEvent.Event

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
    object ItemClicked : AnnouncementEvent()
}