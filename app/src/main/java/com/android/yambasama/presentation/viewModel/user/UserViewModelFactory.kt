package com.android.yambasama.presentation.viewModel.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.domain.usecase.user.*

class UserViewModelFactory(
    private val app: Application,
    private val getUserUseCase: GetUserUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            app,
            getUserUseCase,
            getTokenUseCase,
            saveUserUseCase,
            saveTokenUseCase,
            getSavedUserUseCase,
            getSavedTokenUseCase
        ) as T
    }
}