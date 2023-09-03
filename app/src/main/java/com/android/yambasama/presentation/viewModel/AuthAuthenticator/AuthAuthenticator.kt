package com.android.yambasama.presentation.viewModel.AuthAuthenticator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.presentation.viewModel.user.UserViewModel
import com.android.yambasama.ui.UIEvent.Event.AuthEvent
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataRemote.Token
import com.android.yambasama.domain.usecase.user.GetRefreshTokenUseCase
import com.android.yambasama.domain.usecase.user.GetSavedTokenUseCase
import com.android.yambasama.domain.usecase.user.UpdateSavedTokenUseCase
import com.android.yambasama.ui.UIEvent.ScreenState.AuthScreenState.AuthScreenState

class AuthAuthenticator  @Inject constructor(
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val updateSavedTokenUseCase: UpdateSavedTokenUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase
) : Authenticator {

    private var refreshTokenValue: TokenRoom? = null
    override fun authenticate(route: Route?, response: Response): Request? {


        return runBlocking {
            getSavedTokenUseCase.execute().collect {tokenRoom ->
                refreshTokenValue = tokenRoom
            }

            refreshTokenValue?.let { RefreshBody(refreshToken = it.refreshToken) }?.let {
                val apiResult = getRefreshTokenUseCase.execute(it)
                apiResult.data?.let {apiTokenResponse->
                    updateSavedTokenUseCase.execute(tokenRoom = TokenRoom(
                        id = 1,
                        token = apiTokenResponse.token,
                        refreshToken = apiTokenResponse.refreshToken)
                    )

                }
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${refreshTokenValue!!.token}")
                    .build()
            }


        }
    }

}