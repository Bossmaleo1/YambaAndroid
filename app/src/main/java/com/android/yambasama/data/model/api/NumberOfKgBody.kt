package com.android.yambasama.data.model.api

import com.google.gson.annotations.SerializedName

data class NumberOfKgBody(
    @SerializedName("numberOfKg")
    val numberOfKg: Float,
    @SerializedName("status")
    val status: Boolean
)
