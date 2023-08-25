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
        val tempRoom = getSavedUserUseCase.execute(userToken = "wwxgfdhdjdgfkd")
        tempRoom.test {
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
            Truth.assertThat(userRoom.firstName).isEqualTo("Efrem")
        }
    }
}