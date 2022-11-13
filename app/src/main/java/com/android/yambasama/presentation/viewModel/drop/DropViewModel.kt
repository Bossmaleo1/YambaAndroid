package com.android.yambasama.presentation.viewModel.drop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.yambasama.domain.usecase.user.DeleteTableTokenUseCase
import com.android.yambasama.domain.usecase.user.DeleteTableUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DropViewModel @Inject constructor(
    private val app: Application,
    private val deleteTableUserUseCase: DeleteTableUserUseCase,
    private val deleteTableTokenUseCase: DeleteTableTokenUseCase,
): AndroidViewModel(app) {
    init {

    }

    fun deleteAll() = viewModelScope.launch {
        deleteTableTokenUseCase.execute()
        deleteTableUserUseCase.execute()
    }
}