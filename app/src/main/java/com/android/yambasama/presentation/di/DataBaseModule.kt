package com.android.yambasama.presentation.di

import android.app.Application
import androidx.room.Room
import com.android.yambasama.data.db.dao.AuthenticatorDAO
import com.android.yambasama.data.db.dao.UserDAO
import com.android.yambasama.data.db.dao.YambaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideUserDatabase(app: Application): YambaDatabase {
        return Room.databaseBuilder(app,YambaDatabase::class.java,"yamba_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(wazzabyDatabase: YambaDatabase): UserDAO {
        return wazzabyDatabase.getUserDAO()
    }

    @Singleton
    @Provides
    fun provideAuthenticatorDAO(wazzabyDatabase: YambaDatabase): AuthenticatorDAO {
        return wazzabyDatabase.getAuthenticator()
    }
}