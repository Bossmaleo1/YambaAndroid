package com.android.yambasama.ui.UIEvent.ScreenState.AuthScreenState

import com.android.yambasama.data.model.api.ApiRefreshTokenResponse
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.data.model.dataRemote.Token
import com.android.yambasama.data.model.dataRemote.User

data class AuthScreenState (
    var isNetworkConnected: Boolean = true,
    var isNetworkError: Boolean = false,
    var isLoad: Boolean = false,
    var currentPage: Int = 1,
    var initCallToken: Int = 0,
    var initCallUser: Int = 0,
    var user: User? = null,
    var userRoom: UserRoom? = null,
    var token:Token? = null,
    var refreshToken: ApiRefreshTokenResponse? = null,
    var emailInputValue: String = "",
    var passwordInputValue: String = "",
)