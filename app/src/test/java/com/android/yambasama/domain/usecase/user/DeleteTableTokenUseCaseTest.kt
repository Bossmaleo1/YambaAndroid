package com.android.yambasama.domain.usecase.user

import com.android.yambasama.domain.repository.FakeUserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteTableTokenUseCaseTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var deleteTableTokenUseCase: DeleteTableTokenUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        deleteTableTokenUseCase = DeleteTableTokenUseCase(fakeUserRepository)
    }

    @Test
    fun getDeleteTable_should_be_test() = runTest {
        deleteTableTokenUseCase.execute()
        Truth.assertThat(fakeUserRepository.tokenRooms.size).isEqualTo(0)
    }
}