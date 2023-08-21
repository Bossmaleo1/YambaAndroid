package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.AnnouncementBody
import com.android.yambasama.data.model.api.NumberOfKgBody
import com.android.yambasama.data.model.dataRemote.Announcement
import com.android.yambasama.data.model.dataRemote.Image
import com.android.yambasama.data.model.dataRemote.NumberOfKg
import com.android.yambasama.data.model.dataRemote.PushNotification
import com.android.yambasama.data.model.dataRemote.Status
import com.android.yambasama.data.model.dataRemote.User
import com.android.yambasama.data.util.Resource
import java.util.Date

class FakeAnnouncementRepository: AnnouncementRepository{
    private var shouldReturnNetworkError = false
    val user1: User = User(
        id = 34,
        email = "sidneymaleoregis@gmail.com",
        username = "sidneymaleoregis@gmail.com",
        firstName = "Sidney",
        lastName = "MALEO",
        phone = "+242068125204",
        sex = "M",
        state = "test",
        nationality = "test",
        roles = listOf("ROLE_WRITER"),
        pushNotifications = listOf(PushNotification(id=11, keyPush="xxx")),
        images = listOf(Image(id=1,imageName="imagexxx.png"))
    )
    val announcementResult = Announcement(
        id = 501,
        departureTime = Date(2023, 3,1, 21, 0,0),
        price = 76.0f,
        published = Date(2023, 3,1, 0, 0,0),
        arrivingTime = Date(2023, 1,1, 0, 0,0),
        meetingPlaces1 = "Meeting Place 1",
        meetingPlaces2 = "Meeting Place 2",
        numberOfKgs = listOf(NumberOfKg(
            id = 1,
            numberOfKg = 80.0f,
            status = true,
            published = Date(1, 1,1, 1, 1,1),
            updated = Date(1, 1,1, 1, 1,1)
        )),
        user = user1,
        status = listOf(Status(
            id = 1,
            label = "labelx5",
            published = Date(1, 1,1, 1, 1,1)
        ))
    )

    val announcementResult2 = Announcement(
        id = 502,
        departureTime = Date(2023, 3,1, 21, 0,0),
        price = 80.0f,
        published = Date(2023, 3,1, 0, 0,0),
        arrivingTime = Date(2023, 1,1, 0, 0,0),
        meetingPlaces1 = "Meeting Place 3",
        meetingPlaces2 = "Meeting Place 4",
        numberOfKgs = listOf(NumberOfKg(
            id = 1,
            numberOfKg = 80.0f,
            status = true,
            published = Date(1, 1,1, 1, 1,1),
            updated = Date(1, 1,1, 1, 1,1)
        )),
        user = user1,
        status = listOf(Status(
            id = 1,
            label = "labelx4",
            published = Date(1, 1,1, 1, 1,1)
        ))
    )

    val announcementResult3 = Announcement(
        id = 503,
        departureTime = Date(2023, 4,1, 21, 0,0),
        price = 80.0f,
        published = Date(2023, 3,2, 0, 0,0),
        arrivingTime = Date(2023, 1,1, 0, 0,0),
        meetingPlaces1 = "Meeting Place 3",
        meetingPlaces2 = "Meeting Place 4",
        numberOfKgs = listOf(NumberOfKg(
            id = 1,
            numberOfKg = 81.0f,
            status = true,
            published = Date(1, 1,1, 1, 1,1),
            updated = Date(1, 1,1, 1, 1,1)
        )),
        user = user1,
        status = listOf(Status(
            id = 1,
            label = "labelx3",
            published = Date(1, 1,1, 1, 1,1)
        ))
    )

    val listOfAnnouncement = listOf<Announcement>(
        announcementResult,
        announcementResult2,
        announcementResult3
    )

    val announcementBody = AnnouncementBody(
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

    override suspend fun getAnnouncements(
        page: Int,
        pagination: Boolean,
        arrivingTimeAfter: String,
        arrivingTimeBefore: String,
        departureAddress: Int,
        destinationAddress: Int,
        token: String
    ): Resource<List<Announcement>> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(listOfAnnouncement)
        }
    }

    override suspend fun getAnnouncement(id: Int, token: String): Resource<Announcement> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(announcementResult)
        }
    }

    override suspend fun createAnnouncement(
        announcementBody: AnnouncementBody,
        token: String
    ): Resource<String> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success("success")
        }
    }

}