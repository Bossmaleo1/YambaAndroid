package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.UserRepository

class GetTokenUseCase(private val userRepository: UserRepository) {
    suspend fun execute(userName: String, password: String): Resource<ApiTokenResponse> {
        return userRepository.getToken(userName, password)
    }
}