package com.android.yambasama.domain.usecase.address

import com.android.yambasama.domain.repository.AddressRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAddressUseCaseTest {

    private val mockAddressRepository: AddressRepository = mock()

    @Test
    fun `delete activity interacts with repository`() = runTest {
        // Arrange
        val addresses = GetAddressUseCase(mockAddressRepository)

        // Act
        //verify(mockAddressRepository, times(1)).getAddress("","","","")
    }

}