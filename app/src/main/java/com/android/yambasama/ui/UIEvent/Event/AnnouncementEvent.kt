package com.android.yambasama.ui.UIEvent.Event

import android.content.Context
import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.api.NumberOfKgBody
import com.android.yambasama.data.model.dataRemote.Announcement

sealed class AnnouncementEvent {
    data class AnnouncementInt(
        val app: Context,
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
        val app: Context,
        val announcementBody: AnnouncementBody
    ): AnnouncementEvent()

    data class AnnouncementDetails(
        val app: Context,
        val id: Int
    ): AnnouncementEvent()
    data class IsNetworkConnected(val errorMessage: String): AnnouncementEvent()
    object InitAnnouncementState:AnnouncementEvent()
    data class IsNetworkError(val errorMessage: String):AnnouncementEvent()

    data class IsCreateAnnouncementSuccess(val errorMessage: String):AnnouncementEvent()

    data class IsEmptyAnnouncement(val errorMessage: String):AnnouncementEvent()
    data class ItemClicked(
        val announcement: Announcement
    ) : AnnouncementEvent()
}