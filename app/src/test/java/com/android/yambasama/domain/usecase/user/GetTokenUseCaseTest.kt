package com.android.yambasama.domain.usecase.user

import com.android.yambasama.domain.repository.FakeUserRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetTokenUseCaseTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var getTokenUseCase: GetTokenUseCase

    @Before()
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        getTokenUseCase = GetTokenUseCase(fakeUserRepository)
    }

    @Test
    fun getToken_should_be_test() = runTest {
        val tokenResult = getTokenUseCase.execute(userName = "xxxx", password = "xxxx").data
        Truth.assertThat(tokenResult?.token).isEqualTo("wxxxxxxxxxxxxxxxsjkdjsdhsdgfsdghsdsghgfd")
    }

}