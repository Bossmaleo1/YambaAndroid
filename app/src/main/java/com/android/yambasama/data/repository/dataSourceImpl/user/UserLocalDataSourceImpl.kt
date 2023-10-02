package com.android.yambasama.data.repository.dataSourceImpl.user

import com.android.yambasama.data.db.dao.UserDAO
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.repository.dataSource.user.UserLocalDataSource
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImpl(
    private val userDAO: UserDAO
) : UserLocalDataSource {
    override suspend fun saveUserToDB(user: UserRoom) {
        userDAO.insert(user)
    }

    override fun getSavedUsers(): Flow<List<UserRoom>> {
        return userDAO.getAllUsers()
    }

    override fun getSavedUser(userId: Int): Flow<UserRoom> {
        return userDAO.getUser(userId)
    }

    override suspend fun deleteUserFromDB(user: UserRoom) {
        userDAO.deleteUser(user)
    }


    override suspend fun deleteUserTable() {
        userDAO.deleteTableUser()
    }

}