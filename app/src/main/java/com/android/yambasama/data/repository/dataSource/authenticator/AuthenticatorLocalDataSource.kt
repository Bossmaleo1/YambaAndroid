package com.android.yambasama.data.repository.dataSource.authenticator

import com.android.yambasama.data.model.dataLocal.TokenRoom

interface AuthenticatorLocalDataSource {
    suspend fun updateTokenRoom(token: TokenRoom)

}