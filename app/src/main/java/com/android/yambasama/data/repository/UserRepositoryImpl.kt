package com.android.yambasama.data.repository

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.model.api.RefreshBody
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.model.dataRemote.User
import com.android.yambasama.data.repository.dataSource.user.UserLocalDataSource
import com.android.yambasama.data.repository.dataSource.user.UserRemoteDataSource
import com.android.yambasama.data.util.Resource
import com.android.yambasama.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun getUsers(userName: String, token: String): Resource<List<User>> {
        return responseToResourceUser(userRemoteDataSource.getUser(userName, token))
    }



    private fun responseToResourceUser(response: Response<List<User>>): Resource<List<User>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveUser(user: UserRoom) {
        userLocalDataSource.saveUserToDB(user)
    }

    override suspend fun saveToken(token: TokenRoom) {
        userLocalDataSource.saveTokenToDB(token)
    }



    override suspend fun deleteUser(user: UserRoom) {
        userLocalDataSource.deleteUserFromDB(user)
    }

    override fun getSavedUser(userToken: String): Flow<UserRoom> {
        return userLocalDataSource.getSavedUser(userToken)
    }

    override fun getSavedToken(): Flow<TokenRoom> {
        return userLocalDataSource.getSavedToken()
    }

    override suspend fun deleteUserTable() {
        userLocalDataSource.deleteUserTable()
    }

    override suspend fun deleteTokenTable() {
        userLocalDataSource.deleteTokenTable()
    }

    override suspend fun getToken(userName: String, password: String): Resource<ApiTokenResponse> {
        return responseToResourceToken(userRemoteDataSource.getToken(userName, password))
    }

    private fun responseToResourceToken(response: Response<ApiTokenResponse>): Resource<ApiTokenResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }



    override suspend fun deleteToken(token: TokenRoom) {
        userLocalDataSource.deleteTokenToDB(token)
    }
}