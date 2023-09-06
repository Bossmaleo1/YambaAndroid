package com.android.yambasama.data.api.service

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticatorAPIService {
    @POST("/api/token/refresh")
    suspend fun getRefresh(
        @Body refreshBody: RefreshBody
    ): Response<ApiRefreshTokenResponse>
}