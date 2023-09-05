package com.android.yambasama.presentation.viewModel.AuthAuthenticator

import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.data.model.api.RefreshBody
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.domain.usecase.user.GetRefreshTokenUseCase
import com.android.yambasama.domain.usecase.user.UpdateSavedTokenUseCase
import kotlinx.coroutines.flow.first

class AuthAuthenticator  @Inject constructor(
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val updateSavedTokenUseCase: UpdateSavedTokenUseCase,
    private val tokenManager: TokenManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenRefreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }

        return runBlocking {
                val apiResult = tokenRefreshToken?.let { RefreshBody(refreshToken = it) }
                    ?.let { getRefreshTokenUseCase.execute( refreshBody = it) }
                apiResult?.data?.let { apiTokenResponse ->
                    //we stock our token in DataStore
                    tokenManager.saveToken(apiTokenResponse.token)
                    // we save our refresh token
                    tokenManager.saveRefreshToken(apiTokenResponse.refreshToken)
                    //we upgrade our savedToken
                    updateSavedTokenUseCase.execute(tokenRoom = TokenRoom(
                        id = 1,
                        token = apiTokenResponse.token,
                        refreshToken = apiTokenResponse.refreshToken
                    ))
                }

                response.request.newBuilder()
                    .header("Authorization", "Bearer $tokenRefreshToken")
                    .build()
        }
    }

}