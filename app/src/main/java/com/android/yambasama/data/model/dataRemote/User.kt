package com.android.yambasama.data.model.dataRemote

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("pushNotificationAndroid")
    val pushNotifications: List<PushNotification>?
)
