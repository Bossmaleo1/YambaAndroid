package com.android.yambasama.presentation.viewModel.token

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yambasama.data.db.dataStore.TokenManager

class TokenDataStoreViewModelFactory(
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  TokenDataStoreViewModel(tokenManager) as T
    }
}