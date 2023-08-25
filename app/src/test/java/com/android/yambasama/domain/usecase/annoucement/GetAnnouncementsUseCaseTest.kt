package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.model.dataRemote.Image
import com.android.yambasama.data.model.dataRemote.NumberOfKg
import com.android.yambasama.data.model.dataRemote.PushNotification
import com.android.yambasama.data.model.dataRemote.Status
import com.android.yambasama.data.model.dataRemote.User
import com.android.yambasama.domain.repository.FakeAnnouncementRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.Date


class GetAnnouncementsUseCaseTest {

    private lateinit var getAnnouncementsUseCase: GetAnnouncementsUseCase
    private lateinit var fakeAnnouncementRepository: FakeAnnouncementRepository

    @Before
    fun setUp() {
        fakeAnnouncementRepository = FakeAnnouncementRepository()
        getAnnouncementsUseCase = GetAnnouncementsUseCase(fakeAnnouncementRepository)
    }

    @Test
    fun `Get an Announcements result return`() = runTest {
        val announcements = getAnnouncementsUseCase.execute(
            page = 14,
            pagination = true,
            arrivingTimeAfter = "fake",
            arrivingTimeBefore = "fake",
            departureAddress = 1,
            destinationAddress = 1,
            token = "xxxyzhyeuxbfd"
        )

        Truth.assertThat(announcements.data?.size).isEqualTo(3)
        Truth.assertThat(announcements.data?.size).isNotEqualTo(5)
        Truth.assertThat(announcements.data?.get(0)?.price).isEqualTo(76.0f)
    }

}