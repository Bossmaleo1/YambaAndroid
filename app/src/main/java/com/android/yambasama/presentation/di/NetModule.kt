package com.android.yambasama.presentation.di

import android.content.Context
import com.android.yambasama.BuildConfig
import com.android.yambasama.data.api.interceptor.AuthInterceptor
import com.android.yambasama.data.api.service.AddressAPIService
import com.android.yambasama.data.api.service.AnnouncementAPIService
import com.android.yambasama.data.api.service.UserAPIService
import com.android.yambasama.data.db.dataStore.TokenManager
import com.android.yambasama.domain.usecase.user.GetRefreshTokenUseCase
import com.android.yambasama.domain.usecase.user.GetSavedTokenInterceptorUseCase
import com.android.yambasama.domain.usecase.user.GetSavedTokenUseCase
import com.android.yambasama.domain.usecase.user.UpdateSavedTokenUseCase
import com.android.yambasama.presentation.viewModel.AuthAuthenticator.AuthAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
   /* @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }*/


    @Singleton
    @Provides
    fun provideAuthAuthenticator(
        getRefreshTokenUseCase: GetRefreshTokenUseCase,
        updateSavedTokenUseCase: UpdateSavedTokenUseCase,
        tokenManager: TokenManager
    ): AuthAuthenticator = AuthAuthenticator(
        getRefreshTokenUseCase = getRefreshTokenUseCase,
        updateSavedTokenUseCase = updateSavedTokenUseCase,
        tokenManager = tokenManager
    )

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)


    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager):
            AuthInterceptor = AuthInterceptor(tokenManager)


    @Singleton
    @Provides
    fun provideUserAPIService(retrofit: Retrofit): UserAPIService {
        return retrofit.create(UserAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideAddressAPIService(retrofit: Retrofit): AddressAPIService {
        return retrofit.create(AddressAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideAnnouncementAPIService(retrofit: Retrofit): AnnouncementAPIService {
        return retrofit.create(AnnouncementAPIService::class.java)
    }

    @Singleton
    @Provides
    fun providerRetrofit(
        authInterceptor: AuthInterceptor
    ): Retrofit {
        /*val getSavedTokenUseCase: GetSavedTokenInterceptorUseCase
       val authInterceptor: AuthInterceptor = AuthInterceptor(getSavedTokenUseCase)*/
        /*val  authAuthenticator: AuthAuthenticator*/
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .addInterceptor(authInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_DEV)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}