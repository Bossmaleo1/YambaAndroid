package com.android.yambasama.data.repository

import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.repository.dataSource.annoucement.AnnouncementRemoteDataSource
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository
import retrofit2.Response

class AnnouncementRepositoryImpl(
    private val annoucementRemoteDataSource: AnnouncementRemoteDataSource
): AnnouncementRepository {

    private fun responseToRessourceAnnouncement(response: Response<List<Announcement>>): Resource<List<Announcement>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        arrivingTimeAfter: String,
        arrivingTimeBefore: String,
        departureAddress: Int,
        destinationAddress: Int,
        token: String
    ): Resource<List<Announcement>> {
        return responseToRessourceAnnouncement(
            annoucementRemoteDataSource.getAnnouncements(
                page = page,
                pagination = pagination,
                departureTimeAfter = arrivingTimeAfter,
                departureTimeBefore = arrivingTimeBefore,
                departureAddress = departureAddress,
                destinationAddress = destinationAddress,
                token = token
            )
        )
    }
}