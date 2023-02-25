package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.ApiAnnouncementResponse
import com.android.yambasama.data.util.Resource

interface AnnouncementRepository {
    suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        departureTime: String,
        departureAddress: String,
        destinationAddress: String,
        token: String
    ): Resource<ApiAnnouncementResponse>
}