package com.android.yambasama.presentation.viewModel.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.yambasama.domain.usecase.user.GetSavedUserUseCase
import com.android.yambasama.domain.usecase.user.GetTokenUseCase
import com.android.yambasama.domain.usecase.user.GetUserUseCase
import com.android.yambasama.domain.usecase.user.SaveUserUseCase
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

class UserViewModelTest {

    //subject under test
    private lateinit var userViewModel: UserViewModel
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var getTokenUseCase: GetTokenUseCase
    private lateinit var saveUserUseCase: SaveUserUseCase
    private lateinit var saveTokenUseCase: SaveTokenUseCase
    private lateinit var getSavedUserUseCase: GetSavedUserUseCase
    private lateinit var getSavedTokenUseCase: GetSavedTokenUseCase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        getUserUseCase = Mockito.mock(GetUserUseCase::class.java)
        getTokenUseCase = Mockito.mock(GetTokenUseCase::class.java)
        saveUserUseCase = Mockito.mock(SaveUserUseCase::class.java)
        saveTokenUseCase = Mockito.mock(SaveTokenUseCase::class.java)
        getSavedUserUseCase = Mockito.mock(GetSavedUserUseCase::class.java)
        getSavedTokenUseCase = Mockito.mock(GetSavedTokenUseCase::class.java)
        userViewModel = UserViewModel(
            getUserUseCase,
            getTokenUseCase,
            saveUserUseCase,
            saveTokenUseCase,
            getSavedUserUseCase,
            getSavedTokenUseCase
        )
    }
}