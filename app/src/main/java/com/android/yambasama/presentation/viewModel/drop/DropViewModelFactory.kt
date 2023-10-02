package com.android.yambasama.presentation.viewModel.drop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.domain.usecase.user.DeleteTableUserUseCase

class DropViewModelFactory(
    private val deleteTableUserUseCase: DeleteTableUserUseCase,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DropViewModel(
            deleteTableUserUseCase,
            tokenManager
        ) as T
    }
}