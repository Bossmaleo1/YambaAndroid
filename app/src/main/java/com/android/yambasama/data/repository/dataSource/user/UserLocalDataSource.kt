package com.android.yambasama.data.repository.dataSource.user

import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun saveUserToDB(user: UserRoom)
    suspend fun saveTokenToDB(token: TokenRoom)
    fun getSavedToken(): Flow<TokenRoom>
    fun getSavedUsers(): Flow<List<UserRoom>>
    fun getSavedUser(userToken: String): Flow<UserRoom>
    suspend fun deleteUserFromDB(user: UserRoom)

    suspend fun updateTokenRoom(token: TokenRoom)
    suspend fun deleteTokenToDB(token: TokenRoom)

    suspend fun deleteUserTable()
    suspend fun deleteTokenTable()
}