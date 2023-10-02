package com.android.yambasama.domain.repository

import com.android.yambasama.data.model.api.ApiTokenResponse
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.model.dataRemote.Image
import com.android.yambasama.data.model.dataRemote.PushNotification
import com.android.yambasama.data.model.dataRemote.User
import com.android.yambasama.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository: UserRepository {

    val user1: User = User(
        id = 34,
        email = "sidneymaleoregis@gmail.com",
        username = "sidneymaleoregis@gmail.com",
        firstName = "Sidney",
        lastName = "MALEO",
        phone = "+242068125204",
        sex = "M",
        state = "test",
        nationality = "test",
        roles = listOf("ROLE_WRITER"),
        pushNotifications = listOf(PushNotification(id=11, keyPush="xxx")),
        images = listOf(Image(id=1,imageName="imagexxx.png"))
    )

    val user2: User = User(
        id = 35,
        email = "efremmaleo@gmail.com",
        username = "efremmaleo@gmail.com",
        firstName = "Efrem",
        lastName = "MALEO",
        phone = "+242068125204",
        sex = "M",
        state = "test",
        nationality = "test",
        roles = listOf("ROLE_WRITER"),
        pushNotifications = listOf(PushNotification(id=11, keyPush="xxx")),
        images = listOf(Image(id=1,imageName="imagexxx.png"))
    )

    val userRoom = UserRoom(
        id = 35,
        email = "efremmaleo@gmail.com",
        firstName = "Efrem",
        lastName = "MALEO",
        phone = "+242068125204",
        sex = "M",
        state = "test",
        nationality = "test",
        userToken = "wwxgfdhdjdgfkd",
        pushNotification = "wxwxxwwx",
        imageUrl = "imagexgfsf.png",
        role = "ROLE_ADMIN",
        userName = "Testing"
    )

    val tokenRoom = TokenRoom(1,"xxddjndfhfjf")

    val tokenRooms = mutableListOf<TokenRoom>()
    val userRooms = mutableListOf<UserRoom>()
    val apiTokenResponse = ApiTokenResponse(token = "wxxxxxxxxxxxxxxxsjkdjsdhsdgfsdghsdsghgfd")
    val listOfUser = listOf<User>(user1,user2)


    private var shouldReturnNetworkError = false

    override suspend fun getUsers(userName: String, token: String): Resource<List<User>> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(listOfUser)
        }
    }

    override suspend fun saveUser(user: UserRoom) {
        userRooms.add(user)
    }

    override suspend fun deleteUser(user: UserRoom) {
        userRooms.remove(user)
    }

    override fun getSavedUser(userToken: String): Flow<UserRoom> {
        return flow { emit(userRoom) }
    }

    override suspend fun getToken(userName: String, password: String): Resource<ApiTokenResponse> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(apiTokenResponse)
        }
    }

    override suspend fun saveToken(token: TokenRoom) {
        tokenRooms.add(token)
    }

    override suspend fun deleteToken(token: TokenRoom) {
        tokenRooms.remove(token)
    }

    override fun getSavedToken(): Flow<TokenRoom> {
        return flow { emit(tokenRoom) }
    }

    override suspend fun deleteUserTable() {
        userRooms.removeAll(userRooms)
    }

    override suspend fun deleteTokenTable() {
        tokenRooms.removeAll(tokenRooms)
    }


}