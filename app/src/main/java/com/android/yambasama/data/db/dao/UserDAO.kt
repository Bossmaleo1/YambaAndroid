package com.android.yambasama.data.db.dao

import androidx.room.*
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserRoom)

    @Query("SELECT * FROM user_data_table")
    fun getAllUsers(): Flow<List<UserRoom>>

    @Query("SELECT * FROM user_data_table WHERE user_token= :userToken")
    fun getUser(userToken: String): Flow<UserRoom>

    @Delete()
    suspend fun deleteUser(user: UserRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: TokenRoom)

    @Query("SELECT * FROM token_data_table WHERE token_id=1")
    fun getToken(): Flow<TokenRoom>

    @Delete()
    suspend fun deleteToken(token: TokenRoom)

    @Query("DELETE  FROM user_data_table")
    suspend fun deleteTableUser()

    @Query("DELETE  FROM token_data_table")
    suspend fun deleteTableToken()
}