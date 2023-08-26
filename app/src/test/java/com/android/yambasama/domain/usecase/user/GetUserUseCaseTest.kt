package com.android.yambasama.domain.usecase.user

import com.android.yambasama.domain.repository.FakeUserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest {
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var getUserUseCase: GetUserUseCase

    @Before()
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        getUserUseCase = GetUserUseCase(fakeUserRepository)
    }

    @Test
    fun getUser_should_be_test() = runTest {
        val userResult = getUserUseCase.execute(userName = "xxxcx", token = "xxdd").data
        Truth.assertThat(userResult?.size).isEqualTo(2)
        Truth.assertThat(userResult?.get(0)?.id).isEqualTo(34)
    }

}