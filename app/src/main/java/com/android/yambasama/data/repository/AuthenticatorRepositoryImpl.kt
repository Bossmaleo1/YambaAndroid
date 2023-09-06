package com.android.yambasama.data.repository

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.repository.dataSource.authenticator.AuthenticatorLocalDataSource
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.AuthenticatorRepository
import retrofit2.Response

class AuthenticatorRepositoryImpl(
    private val authenticatorLocalDataSource: AuthenticatorLocalDataSource,
): AuthenticatorRepository {
    override suspend fun updateToken(token: TokenRoom) {
        authenticatorLocalDataSource.updateTokenRoom(token)
    }
}