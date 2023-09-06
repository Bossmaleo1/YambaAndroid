package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.util.Resource

interface AuthenticatorRepository {
    suspend fun updateToken(token: TokenRoom)

}