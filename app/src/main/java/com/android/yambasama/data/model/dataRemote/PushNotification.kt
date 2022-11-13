package com.android.yambasama.data.model.dataRemote

import com.google.gson.annotations.SerializedName

data class PushNotification(
    @SerializedName("id")
    val id: Int,
    @SerializedName("keyPush")
    val keyPush: String
)
