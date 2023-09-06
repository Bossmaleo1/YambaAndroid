package com.android.yambasama.presentation.viewModel.AuthAuthenticator

import com.android.yambasama.BuildConfig
import com.android.yambasama.data.api.service.AuthenticatorAPIService
import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.model.dataLocal.TokenRoom
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import com.android.yambasama.domain.usecase.user.UpdateSavedTokenUseCase
import kotlinx.coroutines.flow.first
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAuthenticator  @Inject constructor(
    private val updateSavedTokenUseCase: UpdateSavedTokenUseCase,
    private val tokenManager: TokenManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenRefreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }

        return runBlocking {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEV)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            val service = retrofit.create(AuthenticatorAPIService::class.java)
            tokenRefreshToken?.let { RefreshBody(refreshToken = it) }
                ?.let { service.getRefresh(it) }?.body()?.let {apiRefreshTokenResponse ->
                    tokenManager.deleteToken()
                    tokenManager.deleteRefreshToken()
                    tokenManager.saveRefreshToken(apiRefreshTokenResponse.refreshToken)
                    tokenManager.saveToken(apiRefreshTokenResponse.token)
                    updateSavedTokenUseCase.execute(tokenRoom = TokenRoom(
                        id = 1,
                        token = apiRefreshTokenResponse.token,
                        refreshToken = apiRefreshTokenResponse.refreshToken
                    )
                    )
                }

            runBlocking {
                val token = tokenManager.getToken().first()
                response.request.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            }


        }
    }

}