package com.android.yambasama.domain.usecase.user

import app.cash.turbine.test
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
class GetSavedTokenUseCaseTest {

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var getSaveTokenUseCase: GetSavedTokenUseCase

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        getSaveTokenUseCase = GetSavedTokenUseCase(fakeUserRepository)
    }

    @Test
    fun getSavedToken_should_be_test() = runTest {
        getSaveTokenUseCase.execute().test {
            val tokenRoom = expectMostRecentItem()
            Truth.assertThat(tokenRoom.id).isEqualTo(1)
            Truth.assertThat(tokenRoom.token).isEqualTo("xxddjndfhfjf")
            //we test set methods
            tokenRoom.id = 2
            tokenRoom.token = "xxddjndfhfjf9393"
            Truth.assertThat(tokenRoom.id).isEqualTo(2)
            Truth.assertThat(tokenRoom.token).isEqualTo("xxddjndfhfjf9393")
        }
    }


}