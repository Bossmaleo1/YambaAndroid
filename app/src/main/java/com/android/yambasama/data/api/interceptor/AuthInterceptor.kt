package com.android.yambasama.data.api.interceptor

import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.domain.usecase.user.GetSavedTokenInterceptorUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken().first()
        }

        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }


}