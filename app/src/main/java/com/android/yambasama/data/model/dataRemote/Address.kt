package com.android.yambasama.data.model.dataRemote

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("id")
    val id: Int,
    @SerializedName("isoCode")
    val isoCode: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("airportName")
    val airportName: String,
    @SerializedName("airportCode")
    val airportCode: String,
    @SerializedName("townName")
    val townName: String,
)
