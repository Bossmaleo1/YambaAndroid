package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository

class GetAnnouncementsUseCase(private val annoucementRepository: AnnouncementRepository) {
    suspend fun execute(
        page: Int,
        pagination: Boolean,
        arrivingTimeAfter: String,
        arrivingTimeBefore: String,
        departureAddress: Int,
        destinationAddress: Int
    ): Resource<List<Announcement>> {
        return annoucementRepository.getAnnouncements(
            page = page,
            pagination = pagination,
            arrivingTimeAfter = arrivingTimeAfter,
            arrivingTimeBefore = arrivingTimeBefore,
            departureAddress = departureAddress,
            destinationAddress = destinationAddress
        )
    }
}