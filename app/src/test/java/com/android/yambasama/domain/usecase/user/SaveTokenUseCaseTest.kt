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
class SaveTokenUseCaseTest {
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var saveTokenUseCase: SaveTokenUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        saveTokenUseCase = SaveTokenUseCase(fakeUserRepository)
    }

    @Test
    fun SaveToken_should_be_test() = runTest {
        fakeUserRepository.tokenRooms.removeAll(fakeUserRepository.tokenRooms)
        saveTokenUseCase.execute(token = TokenRoom(id = 37, token = "tokenxxxffdsfds"))
        Truth.assertThat(fakeUserRepository.tokenRooms.size).isEqualTo(1)
        saveTokenUseCase.execute(token = TokenRoom(id = 40, token = "token2xxxffdsfds"))
        saveTokenUseCase.execute(token = TokenRoom(id = 41, token = "token3xxxffdsfds"))
        Truth.assertThat(fakeUserRepository.tokenRooms.size).isEqualTo(3)
        Truth.assertThat(fakeUserRepository.tokenRooms[2].id).isEqualTo(41)
        Truth.assertThat(fakeUserRepository.tokenRooms[1].token).isEqualTo("token2xxxffdsfds")

    }
}