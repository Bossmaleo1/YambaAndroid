package com.android.yambasama.domain.usecase.user

import com.android.yambasama.domain.repository.FakeUserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteTableUserUseCaseTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var deleteTableUserUseCase: DeleteTableUserUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        deleteTableUserUseCase = DeleteTableUserUseCase(fakeUserRepository)
    }

    @Test
    fun getDeleteTableUser_should_be_test() = runTest {
        deleteTableUserUseCase.execute()
        Truth.assertThat(fakeUserRepository.userRooms.size).isEqualTo(0)
    }

}