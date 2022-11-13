package com.android.yambasama.data.repository.dataSourceImpl

import com.android.yambasama.data.api.service.UserAPIService
import com.android.yambasama.data.model.api.ApiLogin
import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.model.api.ApiUserResponse
import com.android.yambasama.data.repository.dataSource.user.UserRemoteDataSource
import retrofit2.Response

class UserRemoteDataSourceImpl(
    private val userAPIService: UserAPIService
) : UserRemoteDataSource {
    override suspend fun getToken(userName: String, password: String): Response<ApiTokenResponse> {
        return userAPIService.getToken(ApiLogin(userName, password))
    }

    override suspend fun getUser(userName: String, token: String): Response<ApiUserResponse> {
        return userAPIService.getUser(userName, token)
    }
}