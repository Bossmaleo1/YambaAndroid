package com.android.yambasama.data.repository.dataSource.user

import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.model.api.ApiUserResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getToken(userName: String, password: String): Response<ApiTokenResponse>
    suspend fun getUser(userName: String, token: String): Response<ApiUserResponse>
}