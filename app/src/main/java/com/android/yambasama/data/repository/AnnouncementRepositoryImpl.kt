package com.android.yambasama.data.repository

import com.android.yambasama.data.model.api.ApiAnnouncementResponse
import com.android.yambasama.data.repository.dataSource.annoucement.AnnouncementRemoteDataSource
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository
import retrofit2.Response

class AnnouncementRepositoryImpl(
    private val annoucementRemoteDataSource: AnnouncementRemoteDataSource
): AnnouncementRepository {

    private fun responseToRessourceAnnouncement(response: Response<ApiAnnouncementResponse>): Resource<ApiAnnouncementResponse> {
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
        departureTime: String,
        departureAddress: String,
        destinationAddress: String,
        token: String
    ): Resource<ApiAnnouncementResponse> {
        return responseToRessourceAnnouncement(
            annoucementRemoteDataSource.getAnnouncements(
                page = page,
                pagination = pagination,
                departureTime = departureTime,
                departureAddress = departureAddress,
                destinationAddress = destinationAddress,
                token = token
            )
        )
    }
}