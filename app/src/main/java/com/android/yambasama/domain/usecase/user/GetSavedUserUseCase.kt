package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedUserUseCase(private val userRepository: UserRepository) {
    fun execute(userId: Int): Flow<UserRoom> {
        return userRepository.getSavedUser(userId)
    }
}