package com.android.yambasama.presentation.viewModel.address

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.yambasama.domain.usecase.address.GetAddressUseCase
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

class AddressViewModelTest {

    // subject under test
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var getAddressUseCase: GetAddressUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        getAddressUseCase = Mockito.mock(GetAddressUseCase::class.java)
        addressViewModel = AddressViewModel(getAddressUseCase)
    }
}