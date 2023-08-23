package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.api.NumberOfKgBody
import com.android.yambasama.domain.repository.FakeAnnouncementRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class CreateAnnouncementUseCaseTest {

    private lateinit var fakeAnnouncementRepository: FakeAnnouncementRepository
    private lateinit var createAnnouncementUseCase: CreateAnnouncementUseCase

    @Before
    fun setUp() {
        fakeAnnouncementRepository = FakeAnnouncementRepository()
        createAnnouncementUseCase = CreateAnnouncementUseCase(fakeAnnouncementRepository)
    }

    @Test
    fun `We create an announcement`() = runTest {
        var fakeAnnouncementBody = AnnouncementBody(
            departureTime = "2023-03-01T21:00:00+00:00",
            arrivingTime = "2023-06-16T13:48:46+00:00",
            price = 90.0f,
            meetingPlace1 = "Meeting place 1",
            meetingPlace2 = "Meeting place 2",
            user = 34,
            destinationAddress = 403,
            departureAddress = 406,
            numberOfKgs = listOf(
                NumberOfKgBody(
                    numberOfKg = 80.0f,
                    status = true
                )
            )
        )
        val createAnnouncement = createAnnouncementUseCase.execute(
            announcementBody = fakeAnnouncementBody,
            token = "xxxfffrrrtttyuxgsdgysdjhzealkezlk"
        )
        Truth.assertThat(createAnnouncement.data).isEqualTo("success")
    }
}