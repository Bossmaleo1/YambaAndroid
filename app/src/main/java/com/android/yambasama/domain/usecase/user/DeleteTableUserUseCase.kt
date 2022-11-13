package com.android.yambasama.domain.usecase.user

import com.android.yambasama.domain.repository.UserRepository

class DeleteTableUserUseCase (private val userRepository: UserRepository) {
    suspend fun execute() = userRepository.deleteUserTable()
}