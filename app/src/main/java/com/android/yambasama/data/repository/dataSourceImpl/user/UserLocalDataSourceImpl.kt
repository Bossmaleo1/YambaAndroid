package com.android.yambasama.data.repository.dataSourceImpl.user

import com.android.yambasama.data.db.dao.UserDAO
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.repository.dataSource.user.UserLocalDataSource
import kotlinx.coroutines.flow.Flow

class UserLocalDataSourceImpl(
    private val userDAO: UserDAO
) : UserLocalDataSource {
    override suspend fun saveUserToDB(user: UserRoom) {
        userDAO.insert(user)
    }

    override suspend fun saveTokenToDB(token: TokenRoom) {
        userDAO.insertToken(token)
    }

    override fun getSavedToken(): Flow<TokenRoom> {
        return userDAO.getToken()
    }

    override fun getSavedUsers(): Flow<List<UserRoom>> {
        return userDAO.getAllUsers()
    }

    override fun getSavedUser(userToken: String): Flow<UserRoom> {
        return userDAO.getUser(userToken)
    }

    override suspend fun deleteUserFromDB(user: UserRoom) {
        userDAO.deleteUser(user)
    }


    override suspend fun deleteTokenToDB(token: TokenRoom) {
        userDAO.deleteToken(token = token)
    }

    override suspend fun deleteUserTable() {
        userDAO.deleteTableUser()
    }

    override suspend fun deleteTokenTable() {
        userDAO.deleteTableToken()
    }
}