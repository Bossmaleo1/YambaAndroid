package com.android.yambasama.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.android.yambasama.R

fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network =
            connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                return true
            }
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

fun getFieldError(app: Context, passwordInputValue: String, emailInputValue: String): String {
    return if (passwordInputValue.isEmpty() && passwordInputValue.isEmpty()) {
        app.getString(R.string.is_login_empty_2_field)
    } else if (emailInputValue.isEmpty()) {
        app.getString(R.string.is_login_empty_email_field)
    } else if (passwordInputValue.isEmpty()) {
        app.getString(R.string.is_login_empty_password_field)
    } else {
        ""
    }
}