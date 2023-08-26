package com.android.yambasama.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.google.common.truth.Truth
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDAOTest {

    private lateinit var dao: UserDAO
    private lateinit var database: YambaDatabase

    val users = listOf(
        UserRoom(
            id = 11,
            firstName = "MALEO1",
            lastName = "Sidney1",
            role = "Test1",
            phone = "00001",
            nationality = "Congolese1",
            sex = "M1",
            state = "France1",
            userToken = "XXXX1",
            email = "sidneymaleo@gmail.com1",
            userName = "Sidleo1",
            pushNotification = "xxxx1",
            imageUrl = "xxxx1"
        ),
        UserRoom(
            id = 1,
            firstName = "MALEO",
            lastName = "Sidney",
            role = "Test",
            phone = "0000",
            nationality = "Congolese",
            sex = "M",
            state = "France",
            userToken = "XXXX",
            email = "sidneymaleo@gmail.com",
            userName = "Sidleo",
            pushNotification = "xxxx",
            imageUrl = "xxxx"
        )
    )



    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            YambaDatabase::class.java
        ).build()
        dao = database.getUserDAO()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun getUserByToken_should_be_Test() = runTest {
        users.forEach {userRoom ->
            dao.insert(userRoom)
        }

        //we test our get user method
        dao.getUser(userToken = "XXXX").test {
            val user = awaitItem()
            Truth.assertThat(user).isEqualTo(users[1])
            cancel()
        }

        //we test our get user method
        dao.getUser(userToken = "XXXX1").test {
            val user = awaitItem()
            Truth.assertThat(user).isEqualTo(users[0])
            cancel()
        }
    }

    @Test
    fun delete_User_should_be_Test() = runTest {
        dao.insert(user = users[0])
        //we test if we insert
        dao.getAllUsers().test {
            val userResultList = awaitItem()
            assertEquals(true, userResultList.size == 1)
        }
        dao.insert(user = users[1])
        //we test if we make the second insert
        dao.getAllUsers().test {
            val userResultList = awaitItem()
            assertEquals(true, userResultList.size == 2)
        }
        //we delete all
        dao.deleteUser(users[0])
        dao.deleteUser(users[1])
        dao.getAllUsers().test {
            val userResultList = awaitItem()
            assertEquals(true, userResultList.isEmpty())
        }
    }

    @Test
    fun get_All_Users_should_be_Test() = runTest {
        dao.insert(user = users[0])
        val users = dao.getAllUsers()
        users.test {
            val userResultList = awaitItem()
            assertEquals(true, userResultList.size == 1)
        }
    }

    @Test
    fun get__User_should_be_Test() = runTest {
        dao.insert(user = users[0])
        val user = dao.getUser(userToken = "XXXX1")
        user.test {
            val user = awaitItem()
            Truth.assertThat(user?.id).isEqualTo(11)
        }
    }

    @Test
    fun insert_User_should_be_Test() = runTest {
        dao.insert(user = users[0])
        dao.getAllUsers().test {
            val userResultList = awaitItem()
            assertEquals(true, userResultList.size == 1)
        }
    }

    @Test
    fun get_Token_Insert_Token_should_be_Test() = runTest {
        dao.insertToken(token = TokenRoom(id = 35, token= "xxxdffgg"))
        dao.getToken().test {
            val tokenRoom = awaitItem()
            Truth.assertThat(tokenRoom).isEqualTo(null)
        }
    }

}