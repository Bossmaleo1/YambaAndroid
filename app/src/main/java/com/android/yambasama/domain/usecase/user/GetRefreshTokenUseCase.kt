package com.android.yambasama.domain.usecase.user

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.UserRepository

class GetRefreshTokenUseCase(private val userRepository: UserRepository) {
    suspend fun execute(refreshBody: RefreshBody): Resource<ApiRefreshTokenResponse> {
      return  userRepository.getRefreshToken(refreshBody)
    }
}