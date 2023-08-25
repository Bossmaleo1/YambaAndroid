package com.android.yambasama.domain.usecase.user

import app.cash.turbine.test
import com.android.yambasama.domain.repository.FakeUserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetSavedUserUseCaseTest {

    private lateinit var getSavedUserUseCase: GetSavedUserUseCase
    private lateinit var fakeUserRepository: FakeUserRepository

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        getSavedUserUseCase = GetSavedUserUseCase(fakeUserRepository)
    }

    @Test
    fun getSavedUser_should_be_test() = runTest {
        getSavedUserUseCase.execute(userToken = "wwxgfdhdjdgfkd").test {
            val userRoom = expectMostRecentItem()
            Truth.assertThat(userRoom.id).isEqualTo(35)
            Truth.assertThat(userRoom.email).isEqualTo("efremmaleo@gmail.com")
            Truth.assertThat(userRoom.firstName).isEqualTo("Efrem")
            Truth.assertThat(userRoom.lastName).isEqualTo("MALEO")
            Truth.assertThat(userRoom.phone).isEqualTo("+242068125204")
            Truth.assertThat(userRoom.sex).isEqualTo("M")
            Truth.assertThat(userRoom.state).isEqualTo("test")
            Truth.assertThat(userRoom.nationality).isEqualTo("test")
            Truth.assertThat(userRoom.userToken).isEqualTo("wwxgfdhdjdgfkd")
            Truth.assertThat(userRoom.pushNotification).isEqualTo("wxwxxwwx")
            Truth.assertThat(userRoom.imageUrl).isEqualTo("imagexgfsf.png")
            Truth.assertThat(userRoom.role).isEqualTo("ROLE_ADMIN")
            Truth.assertThat(userRoom.userName).isEqualTo("Testing")

            //we test user room set methods
            userRoom.id = 36
            userRoom.email = "efrem1maleo@gmail.com"
            userRoom.firstName = "Efrem1"
            userRoom.lastName = "MAYEKO"
            userRoom.phone = "+242067525204"
            userRoom.sex = "F"
            userRoom.state = "test1"
            userRoom.nationality = "test1"
            userRoom.userToken = "wwxgfdhdjdgfkd1"
            userRoom.pushNotification = "wxwxxwwx1"
            userRoom.imageUrl = "imagexgfs1f.png"
            userRoom.role = "ROLE_ADMIN1"
            userRoom.userName = "Testing1"

            Truth.assertThat(userRoom.id).isEqualTo(36)
            Truth.assertThat(userRoom.email).isEqualTo("efrem1maleo@gmail.com")
            Truth.assertThat(userRoom.firstName).isEqualTo("Efrem1")
            Truth.assertThat(userRoom.lastName).isEqualTo("MAYEKO")
            Truth.assertThat(userRoom.phone).isEqualTo("+242067525204")
            Truth.assertThat(userRoom.sex).isEqualTo("F")
            Truth.assertThat(userRoom.state).isEqualTo("test1")
            Truth.assertThat(userRoom.nationality).isEqualTo("test1")
            Truth.assertThat(userRoom.userToken).isEqualTo("wwxgfdhdjdgfkd1")
            Truth.assertThat(userRoom.pushNotification).isEqualTo("wxwxxwwx1")
            Truth.assertThat(userRoom.imageUrl).isEqualTo("imagexgfs1f.png")
            Truth.assertThat(userRoom.role).isEqualTo("ROLE_ADMIN1")
            Truth.assertThat(userRoom.userName).isEqualTo("Testing1")
        }
    }
}