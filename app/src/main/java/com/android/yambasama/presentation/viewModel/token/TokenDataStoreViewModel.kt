package com.android.yambasama.presentation.viewModel.token

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.yambasama.data.db.dataStore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TokenDataStoreViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    val token = MutableLiveData<String?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getToken().collect {
                withContext(Dispatchers.Main) {
                    token.value = it
                }
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveToken(token)
        }
    }

    fun saveRefreshToken(refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveRefreshToken(refreshToken)
        }
    }

    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteToken()
        }
    }

    fun deleteRefreshToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteRefreshToken()
        }
    }

}