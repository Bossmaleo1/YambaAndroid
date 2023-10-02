package com.android.yambasama.ui.UIEvent.Event

import android.content.Context

sealed class AuthEvent {
    data class GetToken(val app: Context, val userName: String, val password: String): AuthEvent()
    data class GetUser(val app: Context, val userName: String, val token: String): AuthEvent()
    data class GetSavedUser(val userId: Int): AuthEvent()
    object InitUserState: AuthEvent()
    data class IsNetworkConnected(val errorMessage: String): AuthEvent()
    object ConnectionAction: AuthEvent()
    data class IsNetworkError(val errorMessage: String): AuthEvent()
    data class IsEmptyField(val errorMessage: String): AuthEvent()
    data class IsInitField(val email: String, val password: String): AuthEvent()
    data class EmailValueEntered(val value: String): AuthEvent()
    data class PasswordValueEntered(val value: String): AuthEvent()
}