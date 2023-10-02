package com.android.yambasama.data.repository.dataSource.user

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.model.dataRemote.User
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getToken(userName: String, password: String): Response<ApiTokenResponse>
    suspend fun getUser(userName: String): Response<List<User>>

}