package com.android.yambasama.data.model.api

import com.google.gson.annotations.SerializedName

data class AnnouncementBody(
    @SerializedName("departureTime")
    val departureTime: String,
    @SerializedName("arrivingTime")
    val arrivingTime: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("meetingPlace1")
    val meetingPlace1: String,
    @SerializedName("meetingPlace2")
    val meetingPlace2: String,
    @SerializedName("user")
    val user: Int,
    @SerializedName("destinationAddress")
    val destinationAddress: Int,
    @SerializedName("departureAddress")
    val departureAddress: Int,
    @SerializedName("numberOfKgs")
    val numberOfKgs: List<NumberOfKgBody>
)
