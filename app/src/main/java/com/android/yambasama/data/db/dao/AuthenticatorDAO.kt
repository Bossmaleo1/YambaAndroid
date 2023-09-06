package com.android.yambasama.data.db.dao

import androidx.room.Dao
import androidx.room.Update
import com.android.yambasama.data.model.dataLocal.TokenRoom

@Dao
interface AuthenticatorDAO {
    @Update
    suspend fun updateToken(token: TokenRoom)
}