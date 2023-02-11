package com.android.yambasama.ui.UIEvent.Event

import com.android.yambasama.data.model.dataLocal.TokenRoom
import com.android.yambasama.data.model.dataLocal.UserRoom

sealed class AuthEvent {
    data class GetToken(val userName: String, val password: String): AuthEvent()
    data class GetUser(val userName: String, val token: String): AuthEvent()
    data class SaveUser(val user: UserRoom): AuthEvent()
    data class SaveToken(val token: TokenRoom): AuthEvent()
    data class GetSavedUserByToken(val userToken: String): AuthEvent()
    object GetSavedToken: AuthEvent()
    data class DeleteUser(val user: UserRoom): AuthEvent()
    object IsNetworkConnected: AuthEvent()
    object ConnectionAction: AuthEvent()
    object IsNetworkError: AuthEvent()
}