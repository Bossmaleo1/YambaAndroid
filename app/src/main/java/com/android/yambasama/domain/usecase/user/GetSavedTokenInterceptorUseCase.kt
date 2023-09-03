package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedTokenInterceptorUseCase(private val userRepository: UserRepository) {
    fun execute(): Flow<TokenRoom> {
        return userRepository.getSavedToken()
    }
}