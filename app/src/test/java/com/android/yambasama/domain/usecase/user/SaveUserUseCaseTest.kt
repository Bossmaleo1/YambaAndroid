package com.android.yambasama.domain.usecase.user

import com.android.yambasama.domain.repository.FakeUserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SaveUserUseCaseTest {
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var saveUserCase: SaveUserUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        saveUserCase = SaveUserUseCase(fakeUserRepository)
    }

    @Test
    fun SaveUser_should_be_test() = runTest {
        fakeUserRepository.userRooms.removeAll(fakeUserRepository.userRooms)
        saveUserCase.execute(fakeUserRepository.userRoom)
        Truth.assertThat(fakeUserRepository.userRooms[0].id).isEqualTo(35)
        Truth.assertThat(fakeUserRepository.userRooms[0].email).isEqualTo("efremmaleo@gmail.com")
        Truth.assertThat(fakeUserRepository.userRooms.size).isEqualTo(1)
    }
}