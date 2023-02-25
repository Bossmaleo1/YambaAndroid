package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.api.ApiAnnouncementResponse
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository

class GetAnnouncementsUseCase(private val annoucementRepository: AnnouncementRepository) {
    suspend fun execute(
        page: Int,
        pagination: Boolean,
        departureTime: String,
        departureAddress: String,
        destinationAddress: String,
        token: String
    ): Resource<ApiAnnouncementResponse> {
        return annoucementRepository.getAnnouncements(
            page,
            pagination,
            departureTime,
            departureAddress,
            destinationAddress,
            token
        )
    }
}