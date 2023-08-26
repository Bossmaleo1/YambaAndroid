package com.android.yambasama.domain.usecase.user

import com.android.yambasama.domain.repository.FakeUserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteSavedUserUseCaseTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var deleteSavedUseCase: DeleteSavedUserUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        deleteSavedUseCase = DeleteSavedUserUseCase(fakeUserRepository)
    }

    @Test
    fun getDeleteSavedUser_should_be_test() = runTest {
        fakeUserRepository.saveUser(fakeUserRepository.userRoom)
        Truth.assertThat(fakeUserRepository.userRooms.size).isEqualTo(1)
        deleteSavedUseCase.execute(fakeUserRepository.userRoom)
        Truth.assertThat(fakeUserRepository.userRooms.size).isEqualTo(0)
    }
}