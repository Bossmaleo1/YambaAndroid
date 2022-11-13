package com.android.yambasama.data.model.dataRemote

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageName")
    val imageName: String
)
