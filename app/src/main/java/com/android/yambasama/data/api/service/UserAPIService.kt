package com.android.yambasama.data.api.service

import com.android.yambasama.data.model.api.ApiLogin
import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.model.dataRemote.User
import retrofit2.Response
import retrofit2.http.*

interface UserAPIService {
    @POST("/api/login_check")
    suspend fun getToken(
        @Body apiLogin: ApiLogin
    ): Response<ApiTokenResponse>

    @GET("/api/users")
    suspend fun getUser(
        @Query("username")
        userName: String
    ): Response<List<User>>

}