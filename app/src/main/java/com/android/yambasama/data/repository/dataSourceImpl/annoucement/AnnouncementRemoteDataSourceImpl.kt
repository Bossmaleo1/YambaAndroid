package com.android.yambasama.data.repository.dataSourceImpl.annoucement

import com.android.yambasama.data.api.service.AnnouncementAPIService
import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.repository.dataSource.annoucement.AnnouncementRemoteDataSource
import retrofit2.Response

class AnnouncementRemoteDataSourceImpl(
    private val announcementAPIService: AnnouncementAPIService
): AnnouncementRemoteDataSource {
    override suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        departureTimeAfter: String,
        departureTimeBefore: String,
        departureAddress: Int,
        destinationAddress: Int
    ): Response<List<Announcement>> {
        return announcementAPIService.getAnnouncements(
            page = page,
            pagination = pagination,
            arrivingTimeAfter = departureTimeAfter,
            arrivingTimeBefore = departureTimeBefore,
            departureAddress = departureAddress,
            destinationAddress = destinationAddress
        )
    }

    override suspend fun getAnnouncement(id: Int): Response<Announcement> {
        return  announcementAPIService.getAnnouncement(
            id = id
        )
    }

    override suspend fun createAnnouncement(
        announcementBody: AnnouncementBody
    ): Response<String> {
        return announcementAPIService.createAnnouncement(
            announcementBody = announcementBody
        )
    }

}