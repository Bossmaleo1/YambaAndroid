package com.android.yambasama.domain.usecase.address

import com.android.yambasama.domain.repository.AddressRepository
import com.android.yambasama.domain.repository.FakeAddressRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAddressUseCaseTest {

    private lateinit var getAddressUseCase: GetAddressUseCase
    private lateinit var fakeAddressRepository: FakeAddressRepository

    @Before
    fun setUp() {
        fakeAddressRepository = FakeAddressRepository()
        getAddressUseCase = GetAddressUseCase(fakeAddressRepository)
    }

    @Test
    fun `Get Address List correct address return`() = runTest {
        // Arrange
        /*val addresses = getAddressUseCase.execute(locale = "",page = 0,query = "",token="")
        assertThat*/
    }

}