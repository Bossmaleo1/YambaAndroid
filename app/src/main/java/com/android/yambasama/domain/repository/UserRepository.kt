package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.ApiTokenResponse
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
    fun getSavedUser(userId: Int): Flow<UserRoom>

    //resource for retrofit requests
    suspend fun getToken(userName: String, password: String): Resource<ApiTokenResponse>

    suspend fun deleteUserTable()
}