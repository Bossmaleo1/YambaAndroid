package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.api.ApiAnnouncementResponse
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository

class GetAnnouncementsUseCase(private val annoucementRepository: AnnouncementRepository) {
    suspend fun execute(
        page: Int,
        pagination: Boolean,
        departureTimeAfter: String,
        departureTimeBefore: String,
        departureAddress: Int,
        destinationAddress: Int,
        token: String
    ): Resource<List<Announcement>> {
        return annoucementRepository.getAnnouncements(
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