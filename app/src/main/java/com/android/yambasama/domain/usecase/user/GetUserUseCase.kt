package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.api.ApiUserResponse
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(userName: String, token: String): Resource<ApiUserResponse> {
        return userRepository.getUsers(userName, token)
    }
}