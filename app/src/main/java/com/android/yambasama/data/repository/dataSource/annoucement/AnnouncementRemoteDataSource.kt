package com.android.yambasama.data.repository.dataSource.annoucement

import com.android.yambasama.data.model.api.ApiAnnouncementResponse
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.util.Resource
import retrofit2.Response

interface AnnouncementRemoteDataSource {

    suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        departureTimeAfter: String,
        departureTimeBefore: String,
        departureAddress: Int,
        destinationAddress: Int,
        token: String
    ): Response<List<Announcement>>

    suspend fun getAnnouncement(
        id: Int,
        token: String
    ): Response<Announcement>
}