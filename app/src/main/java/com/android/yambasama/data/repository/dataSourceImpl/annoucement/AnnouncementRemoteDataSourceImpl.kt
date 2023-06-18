package com.android.yambasama.data.repository.dataSourceImpl.annoucement

import com.android.yambasama.data.api.service.AnnouncementAPIService
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.repository.dataSource.annoucement.AnnouncementRemoteDataSource
import com.android.yambasama.data.util.Resource
import retrofit2.Response

class AnnouncementRemoteDataSourceImpl(
    private val announcementAPIService: AnnouncementAPIService
): AnnouncementRemoteDataSource {
    override suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        departureTimeAfter: String,
        departureTimeBefore: String,
        departureAddress: String,
        destinationAddress: String,
        token: String
    ): Response<List<Announcement>> {
        return announcementAPIService.getAnnouncements(
            page = page,
            pagination = pagination,
            departureTimeAfter = departureTimeAfter,
            departureTimeBefore = departureTimeBefore,
            departureAddress = departureAddress,
            destinationAddress = destinationAddress,
            token = token
        )
    }

}