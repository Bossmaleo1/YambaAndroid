package com.android.yambasama.presentation.viewModel.drop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.yambasama.domain.usecase.user.DeleteTableTokenUseCase
import com.android.yambasama.domain.usecase.user.DeleteTableUserUseCase
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

class DropViewModelTest {

    // subject under test
    private lateinit var dropViewModel: DropViewModel
    private lateinit var deleteTableUserUseCase: DeleteTableUserUseCase
    private lateinit var deleteTableTokenUseCase: DeleteTableTokenUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        deleteTableUserUseCase = Mockito.mock(DeleteTableUserUseCase::class.java)
        deleteTableTokenUseCase = Mockito.mock(DeleteTableTokenUseCase::class.java)
        dropViewModel = DropViewModel(
            deleteTableUserUseCase,
            deleteTableTokenUseCase
        )
    }
}