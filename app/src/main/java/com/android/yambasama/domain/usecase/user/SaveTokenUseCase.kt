package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.domain.repository.UserRepository

class SaveTokenUseCase(private val userRepository: UserRepository) {
    suspend fun execute(token: TokenRoom) = userRepository.saveToken(token)
}