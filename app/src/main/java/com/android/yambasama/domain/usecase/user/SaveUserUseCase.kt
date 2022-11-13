package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.domain.repository.UserRepository

class SaveUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(user: UserRoom) = userRepository.saveUser(user)
}