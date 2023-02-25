package com.android.yambasama.data.model.dataRemote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NumberOfKg(
    @SerializedName("id")
    val id: Int,
    @SerializedName("numberOfKg")
    val numberOfKg: Float,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("published")
    val published: Date,
    @SerializedName("updated")
    val updated: Date,
)
