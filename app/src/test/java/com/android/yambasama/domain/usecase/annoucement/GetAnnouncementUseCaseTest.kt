package com.android.yambasama.domain.usecase.annoucement

import com.android.yambasama.data.model.dataRemote.Image
import com.android.yambasama.data.model.dataRemote.PushNotification
import com.android.yambasama.data.model.dataRemote.User
import com.android.yambasama.domain.repository.FakeAnnouncementRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetAnnouncementUseCaseTest {

    private lateinit var fakeAnnouncementRepository: FakeAnnouncementRepository
    private lateinit var getAnnouncementUseCase: GetAnnouncementUseCase
    @Before
    fun setUp() {
        fakeAnnouncementRepository = FakeAnnouncementRepository()
        getAnnouncementUseCase = GetAnnouncementUseCase(fakeAnnouncementRepository)
    }

    @Test
    fun `Get an Announcement  return`() = runTest {
        val announcement = getAnnouncementUseCase.execute(
            id = 1,
            token = "xxfhfgfkglk"
        )
        val user: User = User(
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
        Truth.assertThat(announcement.data?.id).isEqualTo(501)
        Truth.assertThat(announcement.data?.price).isEqualTo(76.0f)
        Truth.assertThat(announcement.data?.user).isEqualTo(user)
    }
}