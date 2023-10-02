package com.android.yambasama.presentation.viewModel.drop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.domain.usecase.user.DeleteTableUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DropViewModel @Inject constructor(
    private val deleteTableUserUseCase: DeleteTableUserUseCase,
    private val tokenManager: TokenManager
): ViewModel() {
    init {

    }

    fun deleteAll() = viewModelScope.launch {
        deleteTableUserUseCase.execute()
        tokenManager.deleteToken()
        tokenManager.deleteRefreshToken()
    }
}