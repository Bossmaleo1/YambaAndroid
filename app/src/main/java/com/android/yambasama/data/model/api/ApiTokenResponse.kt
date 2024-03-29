package com.android.yambasama.data.model.api

import com.google.gson.annotations.SerializedName

data class ApiTokenResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
