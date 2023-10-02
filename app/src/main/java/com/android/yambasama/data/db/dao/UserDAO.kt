package com.android.yambasama.data.db.dao

import androidx.room.*
import com.android.yambasama.data.model.dataLocal.UserRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserRoom)

    @Query("SELECT * FROM user_data_table")
    fun getAllUsers(): Flow<List<UserRoom>>

    @Query("SELECT * FROM user_data_table WHERE user_id= :userId")
    fun getUser(userId: Int): Flow<UserRoom>

    @Delete()
    suspend fun deleteUser(user: UserRoom)

    @Query("DELETE  FROM user_data_table")
    suspend fun deleteTableUser()



}