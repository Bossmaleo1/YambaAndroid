package com.android.yambasama.presentation.viewModel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.domain.usecase.user.*

class UserViewModelFactory(
    private val getUserUseCase: GetUserUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val tokenManager: TokenManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            getUserUseCase,
            getTokenUseCase,
            saveUserUseCase,
            getSavedUserUseCase,
            tokenManager
        ) as T
    }
}