package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AnnouncementRepository

class GetAnnouncementUseCase(private val annoucementRepository: AnnouncementRepository) {

    suspend fun execute(
        id: Int,
        token: String
    ): Resource<Announcement> {
        return annoucementRepository.getAnnouncement(id,token)
    }
}