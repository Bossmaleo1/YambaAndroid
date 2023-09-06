package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.util.Resource

interface AnnouncementRepository {
    suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        arrivingTimeAfter: String,
        arrivingTimeBefore: String,
        departureAddress: Int,
        destinationAddress: Int
    ): Resource<List<Announcement>>

    suspend fun getAnnouncement(
        id: Int
    ): Resource<Announcement>

    suspend fun createAnnouncement(
        announcementBody: AnnouncementBody
    ): Resource<String>
}