package com.android.yambasama.data.model.api

import com.android.yambasama.data.model.dataRemote.User
import com.google.gson.annotations.SerializedName

data class ApiLogin(
    val username: String,
    val password: String)
