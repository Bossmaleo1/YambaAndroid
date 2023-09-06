package com.android.yambasama.data.repository

import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.repository.dataSource.annoucement.AnnouncementRemoteDataSource
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository
import retrofit2.Response

class AnnouncementRepositoryImpl(
    private val annoucementRemoteDataSource: AnnouncementRemoteDataSource
): AnnouncementRepository {

    private fun responseToRessourceAnnouncements(response: Response<List<Announcement>>): Resource<List<Announcement>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToRessourceAnnouncement(response: Response<Announcement>): Resource<Announcement> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToRessourceCreateAnnouncement(response: Response<String>): Resource<String> {
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
        return responseToRessourceAnnouncements(
            annoucementRemoteDataSource.getAnnouncements(
                page = page,
                pagination = pagination,
                departureTimeAfter = arrivingTimeAfter,
                departureTimeBefore = arrivingTimeBefore,
                departureAddress = departureAddress,
                destinationAddress = destinationAddress
            )
        )
    }

    override suspend fun getAnnouncement(id: Int, token: String): Resource<Announcement> {
        return  responseToRessourceAnnouncement(
            annoucementRemoteDataSource.getAnnouncement(
                id = id
            )
        )
    }

    override suspend fun createAnnouncement(
        announcementBody: AnnouncementBody,
        token: String
    ): Resource<String> {
        return responseToRessourceCreateAnnouncement(
            annoucementRemoteDataSource.createAnnouncement(
                announcementBody = announcementBody
            )
        )
    }
}