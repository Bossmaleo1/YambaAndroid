package com.android.yambasama.data.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom

@Database(
    entities = [
        UserRoom::class,
        TokenRoom::class
    ],
    version = 1,
    exportSchema = false
)
abstract class YambaDatabase: RoomDatabase() {
    abstract fun getUserDAO():UserDAO
}