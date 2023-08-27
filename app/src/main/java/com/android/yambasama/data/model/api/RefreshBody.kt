package com.android.yambasama.data.model.api

import com.google.gson.annotations.SerializedName

data class RefreshBody (
    @SerializedName("refresh_token")
    val refreshToken: String
)