package com.android.yambasama.data.repository.dataSourceImpl.authenticator

import com.android.yambasama.data.db.dao.AuthenticatorDAO
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.repository.dataSource.authenticator.AuthenticatorLocalDataSource

class AuthenticatorLocalDataSourceImpl(
    private val authenticatorDAO: AuthenticatorDAO
): AuthenticatorLocalDataSource {
    override suspend fun updateTokenRoom(token: TokenRoom) {
        authenticatorDAO.updateToken(token)
    }
}