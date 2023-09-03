package com.android.yambasama.data.api.interceptor

import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.domain.usecase.user.GetSavedTokenInterceptorUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val getSavedTokenUseCase: GetSavedTokenInterceptorUseCase
): Interceptor {

    private var tokenRefreshRoom: TokenRoom? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        runBlocking {
            getSavedTokenUseCase.execute().collect {tokenRoom ->
                tokenRefreshRoom = tokenRoom
            }

            request.addHeader("Authorization", "Bearer ${tokenRefreshRoom!!.token}")

        }

        return chain.proceed(request.build())
    }


}