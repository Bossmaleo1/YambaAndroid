package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.util.Resource

interface AnnouncementRepository {
    suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        departureTimeAfter: String,
        departureTimeBefore: String,
        departureAddress: String,
        destinationAddress: String,
        token: String
    ): Resource<List<Announcement>>
}