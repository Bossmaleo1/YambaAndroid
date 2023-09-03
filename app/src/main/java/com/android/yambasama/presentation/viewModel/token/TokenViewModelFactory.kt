package com.android.yambasama.presentation.viewModel.token

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.data.db.dataStore.TokenManager

class TokenViewModelFactory(
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  TokenViewModel(tokenManager) as T
    }
}