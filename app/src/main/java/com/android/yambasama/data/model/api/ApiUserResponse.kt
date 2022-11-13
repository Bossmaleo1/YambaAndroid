package com.android.yambasama.data.model.api

import com.android.yambasama.data.model.dataRemote.User
import com.google.gson.annotations.SerializedName

data class ApiUserResponse(
    @SerializedName("hydra:member")
    val Users: List<User>
)
