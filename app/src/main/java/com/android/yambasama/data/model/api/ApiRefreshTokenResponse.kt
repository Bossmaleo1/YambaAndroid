package com.android.yambasama.data.model.api

import com.google.gson.annotations.SerializedName

data class ApiRefreshTokenResponse (
    @SerializedName("refresh_token")
    val refreshToken: String
)