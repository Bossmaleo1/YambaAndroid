package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.domain.repository.UserRepository

class UpdateSavedTokenUseCase(private val userRepository: UserRepository)  {
    suspend fun execute(tokenRoom: TokenRoom) = userRepository.updateToken(tokenRoom)
}