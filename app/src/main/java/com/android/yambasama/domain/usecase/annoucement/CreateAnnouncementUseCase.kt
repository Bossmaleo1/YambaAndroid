package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository

class CreateAnnouncementUseCase(private val annoucementRepository: AnnouncementRepository) {

    suspend fun execute(
        announcementBody: AnnouncementBody
    ): Resource<String> {
        return annoucementRepository.createAnnouncement(
            announcementBody = announcementBody
        )
    }
}