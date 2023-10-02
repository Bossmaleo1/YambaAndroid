package com.android.yambasama.data.repository.dataSource.user

import com.android.yambasama.data.model.dataLocal.UserRoom
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun saveUserToDB(user: UserRoom)
    fun getSavedUsers(): Flow<List<UserRoom>>
    fun getSavedUser(userId: Int): Flow<UserRoom>
    suspend fun deleteUserFromDB(user: UserRoom)

    suspend fun deleteUserTable()
}