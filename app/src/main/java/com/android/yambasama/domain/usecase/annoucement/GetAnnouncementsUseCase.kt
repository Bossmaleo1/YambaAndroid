package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.api.ApiAnnouncementResponse
import com.android.yambasama.data.model.dataRemote.Announcement
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
    ): Resource<List<Announcement>> {
        return annoucementRepository.getAnnouncements(
            page = page,
            pagination = pagination,
            departureTimeAfter = departureTime,
            departureTimeBefore = departureTime,
            departureAddress = departureAddress,
            destinationAddress = destinationAddress,
            token = token
        )
    }
}