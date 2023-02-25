package com.android.yambasama.data.model.dataRemote

import com.google.gson.annotations.SerializedName
import java.util.*

data class Announcement(
    @SerializedName("id")
    val id: Int,
    @SerializedName("departureTime")
    val departureTime: Date,
    @SerializedName("arrivingTime")
    val arrivingTime: Date,
    @SerializedName("price")
    val price: Float,
    @SerializedName("meetingPlaces1")
    val meetingPlaces1: String,
    @SerializedName("meetingPlaces2")
    val meetingPlaces2: String,
    @SerializedName("published")
    val published: Date,
    @SerializedName("user")
    val user: User,
    @SerializedName("numberOfKgs")
    val numberOfKgs: List<NumberOfKg>,
    @SerializedName("status")
    val status: List<Status>,

)
