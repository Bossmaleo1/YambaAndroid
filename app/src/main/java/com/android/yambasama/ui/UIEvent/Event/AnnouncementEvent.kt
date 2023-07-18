package com.android.yambasama.ui.UIEvent.Event

import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.api.NumberOfKgBody
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

    data class GenerateAnnouncementBody(
        val departureTime: String,
        val arrivingTime: String,
        val price: Float,
        val meetingPlace1: String,
        val meetingPlace2: String,
        val user: Int,
        val destinationAddress: Int,
        val departureAddress: Int,
        val numberOfKgs: NumberOfKgBody
    ): AnnouncementEvent()

    data class CreateAnnouncement(
        val announcementBody: AnnouncementBody,
        val token: String
    ): AnnouncementEvent()

    data class AnnouncementDetails(
        val token: String,
        val id: Int
    ): AnnouncementEvent()
    object IsNetworkConnected: AnnouncementEvent()
    object InitAnnouncementState:AnnouncementEvent()
    object IsNetworkError:AnnouncementEvent()

    object IsCreateAnnouncementSuccess:AnnouncementEvent()

    object IsEmptyAnnouncement:AnnouncementEvent()
    data class ItemClicked(
        val announcement: Announcement
    ) : AnnouncementEvent()
}