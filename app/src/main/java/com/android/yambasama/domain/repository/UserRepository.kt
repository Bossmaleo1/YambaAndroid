package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.model.dataRemote.User
import com.android.yambasama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    //resource for retrofit requests
    suspend fun getUsers(userName: String, token: String): Resource<List<User>>

    suspend fun saveUser(user: UserRoom)

    suspend fun deleteUser(user: UserRoom)

    //Flow for Room Data backup
    fun getSavedUser(userToken: String): Flow<UserRoom>

    //resource for retrofit requests
    suspend fun getToken(userName: String, password: String): Resource<ApiTokenResponse>

    suspend fun saveToken(token: TokenRoom)

    suspend fun deleteToken(token: TokenRoom)

    //Flow for Room Data backup
    fun getSavedToken(): Flow<TokenRoom>

    suspend fun deleteUserTable()

    suspend fun deleteTokenTable()
}