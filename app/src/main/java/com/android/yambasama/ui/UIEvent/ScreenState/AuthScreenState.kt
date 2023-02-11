package com.android.yambasama.ui.UIEvent.ScreenState

data class AuthScreenState (
    var isNetworkConnected: Boolean = true,
    var isNetworkError: Boolean = false,
    var isLoad: Boolean = false,
    var currentPage: Int = 1,
    var emailInputValue: String = "",
    var passwordInputValue: String = ""
)