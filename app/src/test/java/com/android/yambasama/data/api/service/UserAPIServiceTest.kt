package com.android.yambasama.data.api.service

import com.android.yambasama.data.model.api.ApiLogin
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAPIServiceTest {
    private lateinit var service: UserAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPIService::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getUser_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("usersresponse.json")
            val responseBody = service.getUser("sidneymaleoregis@gmail.com","").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/api/users?username=sidneymaleoregis%40gmail.com")
        }
    }

    @Test
    fun getToken_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("tokenresponse.json")
            val responseBody = service.getToken(ApiLogin("sidneymaleoregis@gmail.com", "Nfkol3324012020@!")).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/api/login_check")
        }
    }

    @Test
    fun getUser_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("usersresponse.json")
            val responseBody = service.getUser("sidneymaleoregis@gmail.com","").body()
            val user = responseBody!!.get(0)
            assertThat(user.firstName).isEqualTo("Sidney")
            assertThat(user.lastName).isEqualTo("MALEO")
            assertThat(user.email).isEqualTo("sidneymaleoregis@gmail.com")
            assertThat(user.roles[0]).isEqualTo("ROLE_WRITER")
        }
    }

    @Test
    fun getToken_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("tokenresponse.json")
            val responseBody = service.getToken(ApiLogin("sidneymaleoregis@gmail.com", "Nfkol3324012020@!")).body()
            val token = responseBody!!.token
            assertThat(token).isEqualTo("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2OTA4MzI5NTksImV4cCI6MTY5MDkxOTM1OSwicm9sZXMiOlsiUk9MRV9XUklURVIiXSwidXNlcm5hbWUiOiJzaWRuZXltYWxlb3JlZ2lzQGdtYWlsLmNvbSJ9.I35qZtehjgko6Ymt0zFFZHqjl1F45u4-2t3MLXna0c6qzzxNYnDp7qShpK8zk3JeEU29DYV1NlH-9fKka92uzuLKxUdJsWel4_2Zh9caDg7BgS9B2s3o8MfdOe1App07dmkcENr7ujgXaJaBxGSSAHJwJHQv1J6QGJlhuzwRJdJhmHfeiQQVvMEsFVcgOcbmcNFNvbInAx3C8UY-msM3CGu35c3eHzRgATc9NAlW4HImCRgthTmIqaDfu2bqWNGUxyeDUaybA2uTmrZO-mPRLPRCI9C3aQ-DsDyqVYIzSzGKS4V4JFDusNXCKkmK9md3RdFbtZCK2l6DnwfIjnL-yQhbrJqKq4z_kmlKkm2sO0xzLugdEOgSksp4XUMrbpQqiQaq8-hRfVqCQKWaNs7C0tiDANKd2nO1c6vMj60zyl45aSX_DwSaLiwRX-ubLYcCUE92-qmMHihYaj5MHMkhpZ-LPRbWWJ9Av9v0rC8dHQSNtusXE7Pcl4jebBAUd9nSk7zfCw-SH4_P4HuyigX9QLLix0WUd5rKcWN62U8HA5oq_kGJUeUyMWfOq93DxpEuaIrjRxbmHoIr6yBjv7ZbmKym04h6LXH00DKd1arC77ve0GTvO3lk5DiIKe0yMP6rDuwRoaQ_dyyq0NSvGXDsySJ1Rpdvu_F0WXPoQFXEgxU")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}