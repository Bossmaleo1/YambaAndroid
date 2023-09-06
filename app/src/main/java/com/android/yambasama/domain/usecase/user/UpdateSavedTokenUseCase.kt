package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.domain.repository.AuthenticatorRepository
import com.android.yambasama.domain.repository.UserRepository

class UpdateSavedTokenUseCase(private val authenticatorRepository: AuthenticatorRepository)  {
    suspend fun execute(tokenRoom: TokenRoom) = authenticatorRepository.updateToken(tokenRoom)
}