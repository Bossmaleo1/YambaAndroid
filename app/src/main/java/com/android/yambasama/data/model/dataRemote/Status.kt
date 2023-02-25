package com.android.yambasama.data.model.dataRemote

import com.google.gson.annotations.SerializedName
import java.util.*

data class Status(
    @SerializedName("id")
    val id: Int,
    @SerializedName("label")
    val label: String,
    @SerializedName("published")
    val published: Date
)
